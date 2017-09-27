package com.gnoemes.bubblenotes.repo.local;

import com.gnoemes.bubblenotes.data.model.Note;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by kenji1947 on 27.09.2017.
 */

public class LocalRepositoryImpl implements LocalRepository {

    @Override
    public Flowable<RealmResults<Note>> loadNotesSorted(Realm realm, String field) {
        return null;
    }

    @Override
    public Observable<String> addNote(Realm realm, String id, String name, int priority) {
        return null;
    }

    @Override
    public Observable<Note> loadNote(Realm realm, String id) {
        return null;
    }

    @Override
    public Observable<String> deleteNote(Realm realm, String id) {
        return null;
    }

    @Override
    public Observable<String> updateNote(Realm realm, String id, String name, int priority) {
        return null;
    }
}
