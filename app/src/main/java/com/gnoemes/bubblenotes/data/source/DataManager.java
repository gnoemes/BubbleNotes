package com.gnoemes.bubblenotes.data.source;

import com.gnoemes.bubblenotes.data.model.Note;

import io.reactivex.Observable;
import io.realm.RealmResults;

/**
 * Created by kenji1947 on 25.09.2017.
 */

public interface DataManager {

    Observable<Note> loadNotes(String id);
    Observable<RealmResults<Note>> loadNotes();
    Observable<Boolean> addNote(String id, String name, int priority);
    Observable<String> addNote(String name, int priority);
    Observable<Boolean> updateNote(String id, String name, int priority);
    Observable<Boolean> deleteNote(String id);
}
