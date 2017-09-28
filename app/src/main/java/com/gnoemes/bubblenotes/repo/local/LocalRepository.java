package com.gnoemes.bubblenotes.repo.local;

import com.gnoemes.bubblenotes.data.model.Note;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by kenji1947 on 27.09.2017.
 */

public interface LocalRepository {
    Flowable<RealmResults<Note>> loadNotesSorted(Realm realm, String field);
    Observable<String> addNote(String id, String name, int priority);
    Observable<Note> loadNote(Realm realm, String id);
    public Observable<String> deleteNote(String id);
    public Observable<String> updateNote(String id, String name, int priority);
}
