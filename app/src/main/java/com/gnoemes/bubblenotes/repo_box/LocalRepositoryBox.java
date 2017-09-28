package com.gnoemes.bubblenotes.repo_box;

import com.gnoemes.bubblenotes.repo_box.model.Note;
import com.gnoemes.bubblenotes.repo_box.model.Note_;
import com.gnoemes.bubblenotes.util.CommonUtils;

import java.util.List;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.Property;
import io.objectbox.query.Query;
import io.objectbox.rx.RxQuery;
import io.reactivex.Observable;

/**
 * Created by kenji1947 on 28.09.2017.
 */

public class LocalRepositoryBox {
    BoxStore boxStore;
    Box<Note> noteBox;

    public void deleteAll() {
        noteBox.removeAll();
    }

    public LocalRepositoryBox(BoxStore boxStore) {
        this.boxStore = boxStore;
        noteBox = boxStore.boxFor(Note.class);
    }

    public Observable<List<Note>> getAllNotesOrderBy(Property property) {
        Query<Note> query2 = noteBox.query().order(property).build();
        return RxQuery.observable(query2);
    }

    public Observable<Note> getNote(long id) {
        return Observable.<Note>fromCallable(() -> {return noteBox.get(id);});
    }

    public Observable<Long> addOrUpdateNote(Note note) {

        return Observable.fromCallable(() -> {
            CommonUtils.longOperation();
            return noteBox.put(note);
        });
    }

    public Observable<Boolean> deleteNote(long id) {
        return Observable.fromCallable(() -> {
            noteBox.remove(id);
            return true;
        });
    }
}

