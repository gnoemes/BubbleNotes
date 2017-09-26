package com.gnoemes.bubblenotes.ui.note_detail;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.gnoemes.bubblenotes.App;
import com.gnoemes.bubblenotes.data.model.Note;
import com.gnoemes.bubblenotes.data.source.DataManager;

import java.util.UUID;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.realm.Realm;
import timber.log.Timber;

/**
 * Created by kenji1947 on 25.09.2017.
 */

@InjectViewState
public class NoteDetailPresenter extends MvpPresenter<NoteDetailView> {

    @Inject
    DataManager dataManager;

    private Realm realm;
    private String id;

    public NoteDetailPresenter(Realm realm, String id) {
        this.realm = realm;
        this.id = id;
        App.getAppComponent().inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        Timber.d("onFirstViewAttach id: " + id);

        //TODO Is in edit mode
        if (id != null)
            getNote(id);
    }

    private void getNote(String id) {

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

//        Note note = realm.where(Note.class).equalTo("id", this.id).findFirst();
    }

    public void addNote(String name, int priority) {
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
                }
            });

//        realm.executeTransactionAsync(realm1 -> {
//                    Note note = realm1.createObject(Note.class, id);
//                    note.setName(name);
//                    note.setPriority(priority);
//                }, () -> {
//                    getViewState().showToast("Note saved " + id);
//                },
//                error -> {
//                    getViewState().showToast("Error when saving");
//                });
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
                    }
                });

//        realm.executeTransactionAsync(realm1 -> {
//                    Note note = realm1.where(Note.class).equalTo("id", id).findFirst();
//                    note.setName(name);
//                    note.setPriority(priority);
//                }, () -> {
//                    getViewState().showToast("Note updated");
//                },
//                error -> {
//                    getViewState().showToast("Error when updating");
//                });
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

//        realm.executeTransactionAsync(realm1 -> {
//            Note note = realm1.where(Note.class).equalTo("id", id).findFirst();
//            note.deleteFromRealm();
//        }, () -> {
//            getViewState().showToast("Note deleted");
//            getViewState().backPressed();
//        });
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
