package com.gnoemes.bubblenotes.data.source;

import com.gnoemes.bubblenotes.data.model.Note;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.realm.Realm;
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

    //----------------------------------

    Flowable<RealmResults<Note>> loadNotesSorted(Realm realm, String field);
    Observable<String> addNoteNew(Realm realm, String id, String name, int priority);
    Observable<Note> loadNote(Realm realm, String id);
    public Observable<String> deleteNote(Realm realm, String id);
    public Observable<String> updateNote(Realm realm, String id, String name, int priority);
}
