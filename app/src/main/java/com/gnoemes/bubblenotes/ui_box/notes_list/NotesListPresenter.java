package com.gnoemes.bubblenotes.ui_box.notes_list;

import android.content.Context;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.gnoemes.bubblenotes.App;
import com.gnoemes.bubblenotes.repo_box.LocalRepositoryBox;
import com.gnoemes.bubblenotes.repo_box.model.Note;
import com.gnoemes.bubblenotes.repo_box.model.Note_;

import java.util.List;

import io.objectbox.BoxStore;
import io.objectbox.android.AndroidScheduler;
import io.objectbox.query.Query;
import io.objectbox.reactive.DataObserver;
import io.objectbox.reactive.DataSubscription;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by kenji1947 on 28.09.2017.
 */

@InjectViewState
public class NotesListPresenter extends MvpPresenter<NotesListView> {
    private LocalRepositoryBox localRepositoryBox;
    Context app;
    DataSubscription subscription;
    Disposable disposable;
    BoxStore boxStore;

    public NotesListPresenter(Context app, LocalRepositoryBox localRepositoryBox) {
      this.localRepositoryBox = localRepositoryBox;
        this.app = app;
        boxStore = ((App)(app)).getBoxStore();
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        deleteAll();
        //dataObserver();
        loadNotes();
    }

    private void deleteAll() {
        localRepositoryBox.deleteAll();
    }

    public void addNote(Note note) {
        localRepositoryBox.addOrUpdateNote(note)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(id -> {
                    Timber.d("addNote showMessage");
                    getViewState().showMessage("Note added " + id);
                }, throwable -> {}, () -> {});
    }

    private void dataObserver() {
        BoxStore boxStore = ((App)(app)).getBoxStore();

        Query<Note> query2 = boxStore.boxFor(Note.class).query().order(Note_.priority).build();
        subscription = query2.subscribe().on(AndroidScheduler.mainThread())
                .observer(new DataObserver<List<Note>>() {
                    @Override
                    public void onData(List<Note> data) {
                        Timber.d("onData");
                        NotesListPresenter.this.getViewState().setNotesList(data);
                    }
                });
    }

    public void loadNotes() {

        disposable = localRepositoryBox.getAllNotesOrderBy(Note_.priority)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Note>>() {
                    @Override
                    public void accept(List<Note> notes) throws Exception {
                        Timber.d("loadNotes setNotesList");
                        NotesListPresenter.this.getViewState().setNotesList(notes);
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
    }
}
