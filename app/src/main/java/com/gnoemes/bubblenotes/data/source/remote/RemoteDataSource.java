package com.gnoemes.bubblenotes.data.source.remote;

import com.gnoemes.bubblenotes.data.model.Note;
import com.gnoemes.bubblenotes.data.source.NoteDataSource;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.realm.RealmResults;
import io.realm.rx.CollectionChange;

public class RemoteDataSource implements NoteDataSource {

    @Override
    public Single<Note> loadNoteById(String id) {
        throw  new UnsupportedOperationException();
    }

    @Override
    public Observable<CollectionChange<RealmResults<Note>>> loadNotes() {
        throw  new UnsupportedOperationException();
    }

    @Override
    public Single<Note> addOrUpdateNote(Note note) {
        throw  new UnsupportedOperationException();
    }

    @Override
    public Completable deleteNote(String id) {
        throw  new UnsupportedOperationException();
    }
}
