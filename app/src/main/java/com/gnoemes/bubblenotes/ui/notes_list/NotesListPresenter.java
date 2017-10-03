package com.gnoemes.bubblenotes.ui.notes_list;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.gnoemes.bubblenotes.repo.local.LocalRepository;
import com.gnoemes.bubblenotes.repo.model.Note;
import com.gnoemes.bubblenotes.repo.model.Note_;
import com.gnoemes.bubblenotes.util.EspressoIdlingResource;

import java.util.List;


import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import timber.log.Timber;

/**
 * Created by kenji1947 on 28.09.2017.
 */

@InjectViewState
public class NotesListPresenter extends MvpPresenter<NotesListView> {

    private Scheduler main;
    private Scheduler io;
    private LocalRepository localRepositoryBox;
    private Disposable disposable;
    private Disposable disposableDescriptions;
    private Disposable disposableComments;
    private Disposable listenerManager;
    private volatile boolean listenForeignUpdates = false;

    public NotesListPresenter(Scheduler main, Scheduler io, LocalRepository localRepositoryBox) {
        this.main = main;
        this.io = io;
        this.localRepositoryBox = localRepositoryBox;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();

        //loadNotes();
        loadNotesAndListenForChanges();
        listenForeignEntitiesUpdateStatus();
        //listenCommentsChanges();
        //listenDescriptionChanges();

    }
    public Observable<List<Note>> listenChangesComments() {
        return localRepositoryBox.getAllComments()
                .flatMap(comments -> {
                    if (listenForeignUpdates) {
                        Timber.d("listenChangesComments NOTIFY");
                        //return localRepositoryBox.getAllNotesOrderBy(Note_.unixTime);
                        return Observable.fromCallable(() ->
                                localRepositoryBox.getAllNotesOrderByData(Note_.unixTime));
                    }
                    else {
                        Timber.d("listenChangesComments CUT");
                        return Observable.never();
                    }
                });
    }

    public Observable<List<Note>> listenChangesDescription() {
        return localRepositoryBox.getAllDescription()
                .flatMap(descriptions -> {
                    if (listenForeignUpdates) {
                        Timber.d("listenChangesDescription NOTIFY");
                        //return localRepositoryBox.getAllNotesOrderBy(Note_.unixTime);
                        return Observable.fromCallable(() ->
                                localRepositoryBox.getAllNotesOrderByData(Note_.unixTime));
                    }
                    else {
                        Timber.d("listenChangesDescription CUT");
                        return Observable.never();
                    }

                });
    }

    private Observable<List<Note>> listenChangesNotes() {
        return localRepositoryBox.getAllNotesOrderBy(Note_.unixTime);
    }

    private void loadNotesAndListenForChanges() {
        EspressoIdlingResource.increment();
        disposable = Observable.merge(listenChangesComments(), listenChangesDescription(), listenChangesNotes())
                //.debounce(5, TimeUnit.MILLISECONDS)
                .observeOn(main)
                .subscribe(notes -> {
                    if (notes != null) {
                        Timber.d("loadNotes onNext");

                        if(!EspressoIdlingResource.getIdlingResource().isIdleNow())
                            EspressoIdlingResource.decrement();

                        getViewState().setNotesList(notes);
                        listenForeignUpdates = true;
                    } else {
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                    if(!EspressoIdlingResource.getIdlingResource().isIdleNow())
                        EspressoIdlingResource.decrement();
                }, () -> {Timber.d("onComplete");});
    }

    public void listenForeignEntitiesUpdateStatus() {
        listenerManager = localRepositoryBox.subscribeToChangeListenerManager()
                .observeOn(main)
                .subscribe(aBoolean -> {
                    listenForeignUpdates = aBoolean;
                }, throwable -> {Timber.d("listenForeignEntitiesUpdateStatus onError " + throwable);
                }, () -> {Timber.d("listenForeignEntitiesUpdateStatus onComplete");});
    }

    //TODO OLD
    //------------------------------------------------------------------------
    private void listenCommentsChanges() {

        disposableComments = localRepositoryBox.getAllComments()
                .flatMap(comments -> {
                    if (listenForeignUpdates) {
                        Timber.d("listenChangesDescription GO");
                        return localRepositoryBox.getAllNotesOrderBy(Note_.unixTime);
                    } else {
                        Timber.d("listenChangesDescription CUT");
                        return Observable.never();
                    }
                })
                .observeOn(main)
                .subscribe(descriptions -> {
                    Timber.d("listenCommentsChanges onNext");
                    if (listenForeignUpdates) {
                        Timber.d("listenCommentsChanges onNext notifyViewState");
                        //getViewState().notifyDescriptionChanged(descriptions);
                    }
                });
    }

    private void listenDescriptionChanges() {
        disposableDescriptions = localRepositoryBox.getAllDescription()
                .flatMap(descriptions -> {
                    Timber.d("listenChangesDescription Fire");
                    return Observable.just(descriptions);
                })
                .flatMap(comments -> {
                    if (listenForeignUpdates) {
                        Timber.d("listenChangesDescription GO");
                        return Observable.fromCallable(() -> {
                            return localRepositoryBox.getAllNotesOrderByData(Note_.unixTime);
                        });
                        //return localRepositoryBox.getAllNotesOrderBy(Note_.unixTime);
                    } else {
                        Timber.d("listenChangesDescription CUT");
                        return Observable.never();
                    }
                })
                .observeOn(main)
                .subscribe(descriptions -> {
                    Timber.d("listenChangesDescription onNext");
                    if (listenForeignUpdates) {
                        Timber.d("listenChangesDescription onNext notifyViewState");
                        //getViewState().notifyDescriptionChanged(descriptions);
                    }
                });
    }

    private void loadNotes() {
        disposable = localRepositoryBox.getAllNotesOrderBy(Note_.unixTime)
                .observeOn(main)
                .subscribe(new Consumer<List<Note>>() {
                    @Override
                    public void accept(List<Note> notes) throws Exception {
                        Timber.d("loadNotes onComplete");
                        getViewState().setNotesList(notes);
                        listenForeignUpdates = true;
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                }, () -> {
                    Timber.d("onComplete");
                });
    }

    //----------------------------------------------------------------------------------
    public void onStop() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposable.dispose();
//        disposableComments.dispose();
       // disposableDescriptions.dispose();
        listenerManager.dispose();
    }
}
