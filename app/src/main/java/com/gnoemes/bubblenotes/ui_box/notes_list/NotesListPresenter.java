package com.gnoemes.bubblenotes.ui_box.notes_list;

import android.content.Context;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.gnoemes.bubblenotes.App;
import com.gnoemes.bubblenotes.repo_box.LocalRepositoryBox;
import com.gnoemes.bubblenotes.repo_box.model.Note;
import com.gnoemes.bubblenotes.repo_box.model.Note_;

import java.util.List;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.android.AndroidScheduler;
import io.objectbox.query.Query;
import io.objectbox.reactive.DataObserver;
import io.objectbox.reactive.DataSubscription;
import io.objectbox.rx.RxQuery;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
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

    public NotesListPresenter(Context app, LocalRepositoryBox localRepositoryBox) {
      this.localRepositoryBox = localRepositoryBox;
        this.app = app;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        deleteAll();
        initObs();
        loadNotes();
    }

    private void initObs() {
        BoxStore boxStore = ((App)(app)).getBoxStore();

        Query<Note> query2 = boxStore.boxFor(Note.class).query().order(Note_.priority).build();
        subscription = query2.subscribe().on(AndroidScheduler.mainThread())
        .observer(new DataObserver<List<Note>>() {
            @Override
            public void onData(List<Note> data) {
                Timber.d("onData");
            }
        });

//        DataSubscription subscription = ((App)(app)).getBoxStore()
//                .subscribe()
//                .on(AndroidScheduler.mainThread())
//                .observer(new DataObserver<List<Note>>() {
//                    @Override
//                    public void onData(List<Note> notes) {
//                        Timber.d("onData");
//
//                    }
//                });

//        DataObserver<Class<Note>> dataObserver = new DataObserver<Class<Note>>() {
//            @Override
//            public void onData(Class<Note> data) {
//                Timber.d("onData");
//            }
//        };
//        ((App)(app)).getBoxStore().subscribe(Note.class).observer(dataObserver);

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

    public void loadNotes() {
        Disposable disposable = localRepositoryBox.getAllNotesOrderBy(Note_.priority)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(notes -> {
                    Timber.d("loadNotes setNotesList");
                    getViewState().setNotesList(notes);
                }, throwable -> {
                    throwable.printStackTrace();
                }, () -> {
                    Timber.d("onComplete");
                });
    }

    public void onStop() {

    }
}
