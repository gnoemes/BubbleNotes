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
import timber.log.Timber;

/**
 * Created by kenji1947 on 25.09.2017.
 */

@InjectViewState
public class NoteDetailPresenter extends MvpPresenter<NoteDetailView> {

    //@Inject
    DataManager dataManager;

    private String id;

    public NoteDetailPresenter(DataManager dataManager, String id) {
        this.id = id;
        this.dataManager = dataManager;
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

        dataManager.loadNotes(id).subscribe(new Observer<Note>() {
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

    public void addNote(String name, int priority) {
        dataManager.addNote(name, priority)
                //.subscribeOn(Schedulers.io())
                .map(s -> {
                    //Note note = realm.getNote(id);
                    //api.send(note);
                    return s;
                })
                //.observeOn(AndroidSchedulers.mainThread())
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

        dataManager.updateNote(id,name,priority)
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Boolean aBoolean) {

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
        dataManager.deleteNote(id)
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Boolean aBoolean) {

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
    }
}
