package com.gnoemes.bubblenotes.repo.local;

import com.gnoemes.bubblenotes.repo.local.LocalRepository;
import com.gnoemes.bubblenotes.repo.model.Note;
import com.gnoemes.bubblenotes.util.CommonUtils;

import java.util.List;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.Property;
import io.objectbox.query.Query;
import io.objectbox.rx.RxQuery;
import io.reactivex.Observable;
import timber.log.Timber;

/**
 * Created by kenji1947 on 28.09.2017.
 */

public class LocalRepositoryImpl implements LocalRepository {
    private BoxStore boxStore;
    private Box<Note> noteBox;

    public LocalRepositoryImpl(BoxStore boxStore) {
        this.boxStore = boxStore;
        noteBox = boxStore.boxFor(Note.class);
    }

    //TODO Возращает из io
    public Observable<List<Note>> getAllNotesOrderBy(Property property) {
        Timber.d("getAllNotesOrderBy");
        Query<Note> query2 = noteBox.query().order(property).build();
        return RxQuery.observable(query2);
    }

    public Observable<Note> getNote(long id) {
        Timber.d("getNote");
        return Observable.<Note>fromCallable(() -> {
            //CommonUtils.longOperation();
            return noteBox.get(id);});
    }

    public Observable<Long> addOrUpdateNote(Note note) {
        Timber.d("addOrUpdateNote");
        return Observable.fromCallable(() -> {
            //CommonUtils.longOperation();
            return noteBox.put(note);
        });
    }

    public Observable<Boolean> deleteNote(long id) {
        Timber.d("deleteNote");
        return Observable.fromCallable(() -> {
            //CommonUtils.longOperation();
            noteBox.remove(id);
            return true;
        });
    }
}

