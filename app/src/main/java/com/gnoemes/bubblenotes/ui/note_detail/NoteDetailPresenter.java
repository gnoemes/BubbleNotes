package com.gnoemes.bubblenotes.ui.note_detail;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.gnoemes.bubblenotes.repo.local.LocalRepository;
import com.gnoemes.bubblenotes.repo.local.LocalRepositoryImpl;
import com.gnoemes.bubblenotes.repo.model.Note;
import com.gnoemes.bubblenotes.util.EspressoIdlingResource;

import io.reactivex.Scheduler;
import timber.log.Timber;

/**
 * Created by kenji1947 on 28.09.2017.
 */

@InjectViewState
public class NoteDetailPresenter extends MvpPresenter<NoteDetailView> {
    private Scheduler main;
    private Scheduler io;
    private LocalRepository localRepositoryBox;
    private long id;

    public NoteDetailPresenter(Scheduler main, Scheduler io, LocalRepository localRepositoryBox, long id) {
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

    public void addNote(Note note) {
        EspressoIdlingResource.increment();

        localRepositoryBox.addNote(note)
                .subscribeOn(io)
                .observeOn(main)
                .subscribe(id -> {
                    Timber.d("addNote onNext");
                    EspressoIdlingResource.decrement();

                    getViewState().showToast("Note added " + id);
                    getViewState().backPressed();
                }, throwable -> {
                    Timber.d("addNote onError " + throwable);
                    EspressoIdlingResource.decrement();
                }, () -> {});
    }

    public void updateNote(Note note) {
        localRepositoryBox.UpdateNote(note)
                .subscribeOn(io)
                .observeOn(main)
                .subscribe(id -> {
                    Timber.d("UpdateNote onNext");
                    getViewState().showToast("Note updated " + id);
                    getViewState().backPressed();
                }, throwable -> {
                    Timber.d("UpdateNote onError " + throwable);
                }, () -> {});
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
