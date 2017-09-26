package com.gnoemes.bubblenotes.data.source;


import com.gnoemes.bubblenotes.data.model.Note;
import com.gnoemes.bubblenotes.data.source.local.RealmManager;
import com.gnoemes.bubblenotes.data.source.remote.RemoteManager;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.realm.RealmResults;

/**
 * DataManagerDefault which controls Realm Database, API
 */

@Singleton
public class DataManagerDefault implements DataManager {

    private final RealmManager realmManager;
    private final RemoteManager remoteDataManager;

    @Inject
    public DataManagerDefault(RealmManager realmManager, RemoteManager remoteDataManager) {
        this.realmManager = realmManager;
        this.remoteDataManager = remoteDataManager;
    }


    @Override
    public Observable<Note> loadNotes(String id) {
        return realmManager.findNoteById(id);
    }

    @Override
    public Observable<RealmResults<Note>> loadNotes() {
        return realmManager.findAllNotes();
    }


    @Override
    public Observable<Boolean> addNote(String id, String name, int priority) {
        return realmManager.addNote(id,name,priority);
    }

    //TODO Refactor
    @Override
    public Observable<String> addNote(String name, int priority) {
        return realmManager.addNote(name,priority);
    }

    @Override
    public Observable<Boolean> updateNote(String id, String name, int priority) {
        return realmManager.updateNote(id,name,priority);
    }

    @Override
    public Observable<Boolean> deleteNote(String id) {
        return realmManager.deleteNote(id);
    }
}
