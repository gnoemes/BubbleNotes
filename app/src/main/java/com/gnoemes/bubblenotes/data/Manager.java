package com.gnoemes.bubblenotes.data;

import com.gnoemes.bubblenotes.data.model.Note;

import io.realm.RealmResults;

public interface Manager {

    /**
     * Realm Database Manager interface
     */
    interface RealmManager {

        RealmResults<Note> loadNotes(Class clazz);
    }

    /**
     * Manages all model-related issues
     */

    interface DataManager {

        RealmResults<Note> loadNotes(Class clazz);
    }
}
