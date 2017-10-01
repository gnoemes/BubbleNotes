package com.gnoemes.bubblenotes.ui.notes_list;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.gnoemes.bubblenotes.repo.local.LocalRepositoryImpl;
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
    private LocalRepositoryImpl localRepositoryBox;
    private Disposable disposable;

    public NotesListPresenter(Scheduler main, Scheduler io, LocalRepositoryImpl localRepositoryBox) {
        this.main = main;
        this.io = io;
        this.localRepositoryBox = localRepositoryBox;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();

        loadNotes();
        //listenDescriptionChanges();
    }

    private void listenDescriptionChanges() {
        localRepositoryBox.getDescriptionByNoteId(1)
                .observeOn(main)
                .subscribe(descriptions -> {
                    Timber.d("listenDescriptionChanges onComplete");
                    getViewState().notifyDescriptionChanged(descriptions);
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
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                }, () -> {
                    Timber.d("onComplete");
                });
    }

    public void addOrUpdateNote(Note note) {
        localRepositoryBox.addNote(note)
                .subscribeOn(io)
                .observeOn(main)
                .subscribe(id -> {
                    Timber.d("addNote onComplete");
                }, throwable -> {}, () -> {});
    }

    public void onStop() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }
}
