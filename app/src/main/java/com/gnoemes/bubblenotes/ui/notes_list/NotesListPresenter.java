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

    private LocalRepository localRepository;

    private Disposable disposableNotes;
    private Disposable disposableListenForeignUpdates;
    private Disposable disposableDescriptions;
    private Disposable disposableComments;

    private volatile boolean receiveNoteForeignChanges = false;

    public NotesListPresenter(Scheduler main, Scheduler io, LocalRepository localRepositoryBox) {
        this.main = main;
        this.io = io;
        this.localRepository = localRepositoryBox;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();

        loadNotesAndObserve();
        observeNoteForeignChangesStatus();

        //loadNotes();
        //listenCommentsChanges();
        //listenDescriptionChanges();

    }
    public Observable<List<Note>> observeComments() {
        return localRepository.getAllComments()
                .flatMap(comments -> {
                    if (receiveNoteForeignChanges) {
                        Timber.d("observeComments NOTIFY");
                        return Observable.fromCallable(() ->
                                localRepository.getAllNotesSortedList(Note_.unixTime));
                    }
                    else {
                        Timber.d("observeComments IGNORE");
                        return Observable.never();
                    }
                });
    }

    public Observable<List<Note>> observeDescriptions() {
        return localRepository.getAllDescription()
                .flatMap(descriptions -> {
                    if (receiveNoteForeignChanges) {
                        Timber.d("observeDescriptions NOTIFY");
                        return Observable.fromCallable(() ->
                                localRepository.getAllNotesSortedList(Note_.unixTime));
                    }
                    else {
                        Timber.d("observeDescriptions IGNORE");
                        return Observable.never();
                    }

                });
    }

    private Observable<List<Note>> observeNotes() {
        return localRepository.getAllNotesSorted(Note_.unixTime);
    }

    private void loadNotesAndObserve() {
        EspressoIdlingResource.increment();
        disposableNotes = Observable.merge(observeComments(), observeDescriptions(), observeNotes())
                .observeOn(main)
                .subscribe(notes -> {
                            Timber.d("loadNotesAndObserve onNext");
                            if (!EspressoIdlingResource.getIdlingResource().isIdleNow())
                                EspressoIdlingResource.decrement();

                            getViewState().setNotesList(notes);
                            receiveNoteForeignChanges = true;

                        }, throwable -> {
                            throwable.printStackTrace();
                            if (!EspressoIdlingResource.getIdlingResource().isIdleNow())
                                EspressoIdlingResource.decrement();
                        },
                        () -> {
                            Timber.d("onComplete");
                        });
    }

    public void observeNoteForeignChangesStatus() {
        disposableListenForeignUpdates = localRepository.observeNoteForeignChangesStatus()
                .observeOn(main)
                .subscribe(aBoolean -> {
                    receiveNoteForeignChanges = aBoolean;
                }, throwable -> {Timber.d("observeNoteForeignChangesStatus onError " + throwable);
                }, () -> {Timber.d("observeNoteForeignChangesStatus onComplete");});
    }


    public void onStop() {

    }

    @Override
    public void onDestroy() {
        Timber.d("onDestroy");
        super.onDestroy();
        disposableNotes.dispose();
        disposableListenForeignUpdates.dispose();
    }

    //TODO OLD
    //------------------------------------------------------------------------
    private void listenCommentsChanges() {

        disposableComments = localRepository.getAllComments()
                .flatMap(comments -> {
                    if (receiveNoteForeignChanges) {
                        Timber.d("observeDescriptions GO");
                        return localRepository.getAllNotesSorted(Note_.unixTime);
                    } else {
                        Timber.d("observeDescriptions CUT");
                        return Observable.never();
                    }
                })
                .observeOn(main)
                .subscribe(descriptions -> {
                    Timber.d("listenCommentsChanges onNext");
                    if (receiveNoteForeignChanges) {
                        Timber.d("listenCommentsChanges onNext notifyViewState");
                        //getViewState().notifyDescriptionChanged(descriptions);
                    }
                });
    }

    private void listenDescriptionChanges() {
        disposableDescriptions = localRepository.getAllDescription()
                .flatMap(descriptions -> {
                    Timber.d("observeDescriptions Fire");
                    return Observable.just(descriptions);
                })
                .flatMap(comments -> {
                    if (receiveNoteForeignChanges) {
                        Timber.d("observeDescriptions GO");
                        return Observable.fromCallable(() -> {
                            return localRepository.getAllNotesSortedList(Note_.unixTime);
                        });
                        //return localRepository.getAllNotesSorted(Note_.unixTime);
                    } else {
                        Timber.d("observeDescriptions CUT");
                        return Observable.never();
                    }
                })
                .observeOn(main)
                .subscribe(descriptions -> {
                    Timber.d("observeDescriptions onNext");
                    if (receiveNoteForeignChanges) {
                        Timber.d("observeDescriptions onNext notifyViewState");
                        //getViewState().notifyDescriptionChanged(descriptions);
                    }
                });
    }

    private void loadNotes() {
        disposableNotes = localRepository.getAllNotesSorted(Note_.unixTime)
                .observeOn(main)
                .subscribe(new Consumer<List<Note>>() {
                    @Override
                    public void accept(List<Note> notes) throws Exception {
                        Timber.d("loadNotes onComplete");
                        getViewState().setNotesList(notes);
                        receiveNoteForeignChanges = true;
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                }, () -> {
                    Timber.d("onComplete");
                });
    }
}
