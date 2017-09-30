package com.gnoemes.bubblenotes.ui.note_detail;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.gnoemes.bubblenotes.repo.local.LocalRepositoryImpl;
import com.gnoemes.bubblenotes.repo.model.Comment;
import com.gnoemes.bubblenotes.repo.model.Description;
import com.gnoemes.bubblenotes.repo.model.Note;

import java.util.List;

import io.objectbox.relation.ToMany;
import io.objectbox.relation.ToOne;
import io.reactivex.Scheduler;
import timber.log.Timber;

/**
 * Created by kenji1947 on 28.09.2017.
 */

@InjectViewState
public class NoteDetailPresenter extends MvpPresenter<NoteDetailView> {
    private Scheduler main;
    private Scheduler io;
    private LocalRepositoryImpl localRepositoryBox;
    private long id;

    public NoteDetailPresenter(Scheduler main, Scheduler io, LocalRepositoryImpl localRepositoryBox, long id) {
        this.main = main;
        this.io = io;
        this.localRepositoryBox = localRepositoryBox;
        this.id = id;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        Timber.d("onFirstViewAttach");
        if (id != -1)
            getNote(id);
    }

    public void getNote(long id) {
        Timber.d("getNote");
        localRepositoryBox.getNote(id)
                .subscribeOn(io)
                .observeOn(main)
                .subscribe(note -> {
                    getViewState().setNote(note);
                }, throwable -> {
                    getViewState().showToast("Error " + throwable);
                    throwable.printStackTrace();
                });
    }

    public void addOrUpdateNote(Note note) {
        localRepositoryBox.addOrUpdateNote(note)
                .subscribeOn(io)
                .observeOn(main)
                .subscribe(id -> {
                    Timber.d("addNote showMessage");
                    getViewState().showToast("Note added " + id);
                    getViewState().backPressed();
                }, throwable -> {}, () -> {});
    }

    public void deleteNote(long id) {
        localRepositoryBox.deleteNote(id)
                .subscribeOn(io)
                .observeOn(main)
                .subscribe(aBoolean -> {
                    getViewState().showToast("Note deleted " + id);
                    getViewState().backPressed();
                }, throwable -> {

                }, () -> {

                });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
