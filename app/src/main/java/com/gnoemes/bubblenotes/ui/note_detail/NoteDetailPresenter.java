package com.gnoemes.bubblenotes.ui.note_detail;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.gnoemes.bubblenotes.App;
import com.gnoemes.bubblenotes.data.model.Note;
import com.gnoemes.bubblenotes.data.source.DataManager;

import java.util.Observable;
import java.util.UUID;

import javax.inject.Inject;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
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
    Realm realm;
    Scheduler main;
    Scheduler io;

    private String id;

    public NoteDetailPresenter(Realm realm, Scheduler main, Scheduler io, DataManager dataManager, String id) {
        this.id = id;
        this.dataManager = dataManager;
        this.realm = realm;
        this.main = main;
        this.io = io;

        Timber.d("dataManager == null: " + (dataManager == null));
    }

    @Override
    protected void onFirstViewAttach() {
        Timber.d("onFirstViewAttach id: " + id);

        //TODO Is in edit mode
        if (id != null)
            getNote(id);
    }
    //TODO changed method to public from private for test purpose
    public void getNote(String id) {
        dataManager.loadNote(realm, id).subscribe(new Observer<Note>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Note note) {
                getViewState().setNote(note);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {

            }
        });

    }

    //TODO Original method
    public void addNoteT(String name, int priority) {
        String id = UUID.randomUUID().toString();
        dataManager.addNote(id,name,priority)
            .subscribe(new Observer<Boolean>() {
                @Override
                public void onSubscribe(@NonNull Disposable d) {

                }

                @Override
                public void onNext(@NonNull Boolean aBoolean) {

                }

                @Override
                public void onError(@NonNull Throwable e) {
                    getViewState().showToast("Error when saving");
                }

                @Override
                public void onComplete() {
                    getViewState().showToast("Note saved " + id);
                    getViewState().backPressed();
                }
            });
    }

    public void addNote(String id, String name, int priority) {
        dataManager.addNoteNew(realm, id, name, priority)
                .subscribeOn(io)
                .observeOn(main)
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull String s) {
                        getViewState().showToast("Note saved " + s);
                        getViewState().backPressed();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        getViewState().showToast("Error when saving");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void updateNote(String id, String name, int priority) {

        dataManager.updateNote(realm, id,name,priority)
                .subscribeOn(io)
                .observeOn(main)
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull String s) {

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        e.printStackTrace();
                        getViewState().showToast("Error when updating");
                    }

                    @Override
                    public void onComplete() {
                        getViewState().showToast("Note updated");
                        getViewState().backPressed();
                    }
                });
    }
    public void deleteNote(String id) {
        dataManager.deleteNote(realm, id)
                .subscribeOn(io)
                .observeOn(main)
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull String s) {

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        getViewState().showToast("Note deleted");
                        getViewState().backPressed();
                    }
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
