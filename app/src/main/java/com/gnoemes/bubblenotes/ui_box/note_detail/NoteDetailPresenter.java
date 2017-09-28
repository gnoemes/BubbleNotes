package com.gnoemes.bubblenotes.ui_box.note_detail;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.gnoemes.bubblenotes.repo_box.LocalRepositoryBox;
import com.gnoemes.bubblenotes.repo_box.model.Note;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by kenji1947 on 28.09.2017.
 */

@InjectViewState
public class NoteDetailPresenter extends MvpPresenter<NoteDetailView> {
    private LocalRepositoryBox localRepositoryBox;
    private long id;

    public NoteDetailPresenter(LocalRepositoryBox localRepositoryBox, long id) {
        this.localRepositoryBox = localRepositoryBox;
        this.id = id;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        if (id != -1)
            getNote(id);
    }

    public void getNote(long id) {
        localRepositoryBox.getNote(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(note -> {
                    getViewState().setNote(note);
                }, throwable -> {
                    getViewState().showToast("Error " + throwable);
                    throwable.printStackTrace();
                });
    }

    public void addOrUpdateNote(Note note) {
        localRepositoryBox.addOrUpdateNote(note)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(id -> {
                    Timber.d("addNote showMessage");
                    getViewState().showToast("Note added " + id);
                    getViewState().backPressed();
                }, throwable -> {}, () -> {});
    }

    public void deleteNote(long id) {
        localRepositoryBox.deleteNote(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
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
