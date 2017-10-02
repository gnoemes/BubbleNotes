package com.gnoemes.bubblenotes.ui.notes_list;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.gnoemes.bubblenotes.repo.local.LocalRepository;
import com.gnoemes.bubblenotes.repo.model.Note;
import com.gnoemes.bubblenotes.repo.model.Note_;

import java.util.List;

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
    private boolean listenForeignUpdates = true;

    public NotesListPresenter(Scheduler main, Scheduler io, LocalRepository localRepositoryBox) {
        this.main = main;
        this.io = io;
        this.localRepositoryBox = localRepositoryBox;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();

        loadNotes();
        listenForeignEntitiesUpdateStatus();
        listenCommentsChanges();
        listenDescriptionChanges();

    }

    public void listenForeignEntitiesUpdateStatus() {
        listenerManager = localRepositoryBox.subscribeToChangeListenerManager()
                .observeOn(main)
                .subscribe(aBoolean -> {
                    Timber.d("listenForeignEntitiesUpdateStatus " + aBoolean);
                    listenForeignUpdates = aBoolean;
                }, throwable -> {
                    Timber.d("listenForeignEntitiesUpdateStatus onError " + throwable);
                }, () -> {
                    Timber.d("listenForeignEntitiesUpdateStatus onComplete");
                });
    }

    private void listenCommentsChanges() {
        disposableComments = localRepositoryBox.getAllComments()
                .observeOn(main)
                .subscribe(descriptions -> {
                    if (listenForeignUpdates)
                        Timber.d("listenCommentsChanges onComplete");
                    //getViewState().notifyDescriptionChanged(descriptions);
                });
    }

    private void listenDescriptionChanges() {
        disposableDescriptions = localRepositoryBox.getAllDescription()
                .observeOn(main)
                .subscribe(descriptions -> {
                    if (listenForeignUpdates)
                        Timber.d("listenDescriptionChanges onComplete");
                    //getViewState().notifyDescriptionChanged(descriptions);
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

    public void onStop() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposable.dispose();
        disposableComments.dispose();
        disposableDescriptions.dispose();
        listenerManager.dispose();
    }
}
