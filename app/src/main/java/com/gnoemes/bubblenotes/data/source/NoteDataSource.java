package com.gnoemes.bubblenotes.data.source;

import com.gnoemes.bubblenotes.data.model.Note;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.realm.RealmResults;
import io.realm.rx.CollectionChange;

/**
 * Created by kenji1947 on 25.09.2017.
 */

public interface NoteDataSource {
    Single<Note> loadNoteById(String id);
    Observable<CollectionChange<RealmResults<Note>>> loadNotes();
    Single<Note> addOrUpdateNote(Note note);
    Completable deleteNote(String id);
}
