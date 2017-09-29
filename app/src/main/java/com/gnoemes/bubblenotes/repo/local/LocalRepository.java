package com.gnoemes.bubblenotes.repo.local;

import com.gnoemes.bubblenotes.repo.model.Note;

import java.util.List;

import io.objectbox.Property;
import io.reactivex.Observable;

/**
 * Created by kenji1947 on 29.09.2017.
 */

public interface LocalRepository {

    Observable<List<Note>> getAllNotesOrderBy(Property property);
    Observable<Note> getNote(long id);
    Observable<Long> addOrUpdateNote(Note note);
    Observable<Boolean> deleteNote(long id);
}
