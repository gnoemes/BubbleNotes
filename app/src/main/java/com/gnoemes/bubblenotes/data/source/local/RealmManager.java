package com.gnoemes.bubblenotes.data.source.local;

import com.gnoemes.bubblenotes.App;
import com.gnoemes.bubblenotes.data.source.Manager;
import com.gnoemes.bubblenotes.data.model.Note;

import javax.inject.Inject;

import io.realm.RealmResults;

public class RealmManager implements Manager.RealmManager {

    @Inject
    RealmDatabase realmDatabase;

    public RealmManager() {
        App.getAppComponent().inject(this);
    }


    @Override
    public RealmResults<Note> loadNotes(Class clazz) {
        return realmDatabase.find(clazz);
    }
}
