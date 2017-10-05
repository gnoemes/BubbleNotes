package com.gnoemes.bubblenotes.repo.local;

import com.gnoemes.bubblenotes.repo.model.Comment;
import com.gnoemes.bubblenotes.repo.model.Description;
import com.gnoemes.bubblenotes.repo.model.Note;

import java.util.List;

import io.objectbox.Property;
import io.reactivex.Observable;

/**
 * Created by kenji1947 on 29.09.2017.
 */

public interface LocalRepository {

    Observable<Note> getNote(long id);
    Observable<Long> addNote(Note note);
    Observable<Long> UpdateNote(Note note);
    Observable<Boolean> deleteNote(long id);

    List<Note> getAllNotesSortedList(Property property);
    Observable<List<Note>> getAllNotesSorted(Property property);

    Observable<Boolean> observeNoteForeignChangesStatus();

    Observable<List<Comment>> getAllComments();
    Observable<List<Description>> getAllDescription();

}
