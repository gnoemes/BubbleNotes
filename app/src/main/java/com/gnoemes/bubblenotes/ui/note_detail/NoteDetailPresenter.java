package com.gnoemes.bubblenotes.ui.note_detail;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.gnoemes.bubblenotes.model.Note;

import java.util.UUID;

import io.realm.Realm;
import timber.log.Timber;

/**
 * Created by kenji1947 on 25.09.2017.
 */

@InjectViewState
public class NoteDetailPresenter extends MvpPresenter<NoteDetailView> {

    private Realm realm;
    private String id;

    public NoteDetailPresenter(Realm realm, String id) {
        this.realm = realm;
        this.id = id;
    }

    @Override
    protected void onFirstViewAttach() {
        Timber.d("onFirstViewAttach id: " + id);

        //TODO Is in edit mode
        if (id != null)
            loadNote();
    }

    private void loadNote() {
        Note note = realm.where(Note.class).equalTo("id", id).findFirst();
        getViewState().setNote(note);
    }

    public void addNote(String name, int priority) {
        String id = UUID.randomUUID().toString();
        realm.executeTransactionAsync(realm1 -> {
                    Note note = realm1.createObject(Note.class, id);
                    note.setName(name);
                    note.setPriority(priority);
                }, () -> {
                    getViewState().showToast("Note saved " + id);
                },
                error -> {
                    getViewState().showToast("Error when saving");
                });
    }

    public void updateNote(String id, String name, int priority) {
        realm.executeTransactionAsync(realm1 -> {
                    Note note = realm1.where(Note.class).equalTo("id", id).findFirst();
                    note.setName(name);
                    note.setPriority(priority);
                }, () -> {
                    getViewState().showToast("Note updated");
                },
                error -> {
                    getViewState().showToast("Error when updating");
                });
    }
    public void deleteNote(String id) {
        realm.executeTransactionAsync(realm1 -> {
            Note note = realm1.where(Note.class).equalTo("id", id).findFirst();
            note.deleteFromRealm();
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
