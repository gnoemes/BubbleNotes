package com.gnoemes.bubblenotes.data.source;

import com.gnoemes.bubblenotes.data.model.Note;

import io.realm.RealmResults;

/**
 * Created by kenji1947 on 25.09.2017.
 */

public interface DataManager {
    RealmResults<Note> loadNotes(Class clazz);
}
