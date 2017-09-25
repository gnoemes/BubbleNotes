package com.gnoemes.bubblenotes.data.source;


import com.gnoemes.bubblenotes.data.model.Note;
import com.gnoemes.bubblenotes.data.source.remote.IRemoteDataManager;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.realm.RealmResults;

/**
 * DataManager which controls Realm Database, API
 */

@Singleton
public class DataManager implements Manager.DataManager {

    private final Manager.RealmManager realmManager;
    private final IRemoteDataManager remoteDataManager;

    @Inject
    public DataManager(Manager.RealmManager realmManager, IRemoteDataManager remoteDataManager) {
        this.realmManager = realmManager;
        this.remoteDataManager = remoteDataManager;
    }

    @Override
    public RealmResults<Note> loadNotes(Class clazz) {
        return realmManager.loadNotes(clazz);
    }
}
