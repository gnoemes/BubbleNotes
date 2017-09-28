package com.gnoemes.bubblenotes.data.source.local;

import com.gnoemes.bubblenotes.data.model.Note;
import com.gnoemes.bubblenotes.data.source.NoteDataSource;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.rx.CollectionChange;

public class NoteLocalDataSource implements NoteDataSource {

    private final Realm realm;

    @Inject
    public NoteLocalDataSource(Realm realm) {
        this.realm = realm;
    }

    @Override
    public Single<Note> loadNoteById(String id) {
        Note note = realm.where(Note.class).equalTo(Note.ID,id).findFirst();
        return Single.just(note);
    }

    @Override
    public Observable<CollectionChange<RealmResults<Note>>> loadNotes() {
        RealmResults<Note> notes = realm.where(Note.class).findAllSorted(Note.SORT_PRIORITY);
        return notes.isEmpty() ? Observable.empty() : notes.asChangesetObservable();
    }

    @Override
    public Single<Note> addOrUpdateNote(Note note) {
        realm.executeTransaction(realm1 -> {
            realm.copyToRealmOrUpdate(note);
        });
        return Single.just(note);
    }

    @Override
    public Completable deleteNote(String id) {
        return Completable.fromAction(() -> realm.executeTransaction(realm1 -> {
            Note toDelete = realm.where(Note.class).equalTo(Note.ID,id).findFirst();
            if(toDelete != null) {
                toDelete.deleteFromRealm();
            }
        }));
    }
}
