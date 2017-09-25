package com.gnoemes.bubblenotes.data;


import com.gnoemes.bubblenotes.data.model.Note;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.realm.RealmResults;

/**
 * DataManager which controls Realm Database, API
 */

@Singleton
public class DataManager implements Manager.DataManager {

    private final Manager.RealmManager realmManager;

    @Inject
    public DataManager(Manager.RealmManager realmManager) {
        this.realmManager = realmManager;
    }

    @Override
    public RealmResults<Note> loadNotes(Class clazz) {
        return realmManager.loadNotes(clazz);
    }
}
