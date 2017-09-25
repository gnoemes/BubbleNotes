package com.gnoemes.bubblenotes.data.source;


import com.gnoemes.bubblenotes.data.model.Note;
import com.gnoemes.bubblenotes.data.source.local.RealmManager;
import com.gnoemes.bubblenotes.data.source.remote.RemoteManager;

import javax.inject.Inject;
import javax.inject.Singleton;

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
    public RealmResults<Note> loadNotes(Class clazz) {
        return realmManager.loadNotes(clazz);
    }
}
