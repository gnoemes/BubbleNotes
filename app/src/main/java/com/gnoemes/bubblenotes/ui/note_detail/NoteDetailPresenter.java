package com.gnoemes.bubblenotes.ui.note_detail;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.gnoemes.bubblenotes.App;
import com.gnoemes.bubblenotes.data.model.Note;
import com.gnoemes.bubblenotes.data.source.DataManager;
import com.gnoemes.bubblenotes.repo.local.LocalRepository;

import java.util.Observable;
import java.util.UUID;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import timber.log.Timber;

/**
 * Created by kenji1947 on 25.09.2017.
 */

@InjectViewState
public class NoteDetailPresenter extends MvpPresenter<NoteDetailView> {

    //@Inject
    DataManager dataManager;
    LocalRepository localRepository;
    Realm realm;
    Scheduler main;
    Scheduler io;

    private String id;

    public NoteDetailPresenter(Realm realm,
                               Scheduler main,
                               Scheduler io,
                               DataManager dataManager,
                               LocalRepository localRepository,
                               String id) {
        this.id = id;
        this.dataManager = dataManager;
        this.localRepository = localRepository;
        this.realm = realm;
        this.main = main;
        this.io = io;
    }

    @Override
    protected void onFirstViewAttach() {
        Timber.d("onFirstViewAttach id: " + id);
        if (id != null)
            getNote(id);
    }

    //TODO changed method to public from private for test purpose
    public void getNote(String id) {
        localRepository.loadNote(realm, id)
                .subscribe(note -> {
                    getViewState().setNote(note);
                }, throwable -> {
                    throwable.printStackTrace();
                }, () -> {}
                );
    }

    public void addNote(String id, String name, int priority) {
        localRepository.addNote(id, name, priority)
                .subscribeOn(io)
                .observeOn(main)
                .subscribe(s -> {
                    getViewState().showToast("Note saved " + s);
                    getViewState().backPressed();
                }, throwable -> {
                    getViewState().showToast("Error when saving");
                }, () -> {});
    }

    public void updateNote(String id, String name, int priority) {

        localRepository.updateNote(id, name, priority)
                .subscribeOn(io)
                .observeOn(main)
                .subscribe(s -> {
                        },
                        throwable -> {
                            throwable.printStackTrace();
                            getViewState().showToast("Error when updating");
                        },
                        () -> {
                            getViewState().showToast("Note updated");
                            getViewState().backPressed();
                        });
    }
    public void deleteNote(String id) {
        localRepository.deleteNote(id)
                .subscribeOn(io)
                .observeOn(main)
                .subscribe(s -> {
                }, throwable -> {
                    throwable.printStackTrace();
                }, () -> {
                    getViewState().showToast("Note deleted");
                    getViewState().backPressed();
                });
    }
    void onStop() {
        Timber.d("onStop");

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}
