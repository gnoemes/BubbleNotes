package com.gnoemes.bubblenotes.data.source.local;

import com.gnoemes.bubblenotes.data.model.Note;

import io.reactivex.Observable;
import io.realm.RealmResults;

/**
 * Created by kenji1947 on 25.09.2017.
 */

public interface RealmManager {
    Observable<Note> findById(String id);
    Observable<RealmResults<Note>> findAll();
    Observable<Boolean> addNote(String id, String name, int priority);
    Observable<Boolean> updateNote(String id, String name, int priority);
    Observable<Boolean> deleteNote(String id);
}
