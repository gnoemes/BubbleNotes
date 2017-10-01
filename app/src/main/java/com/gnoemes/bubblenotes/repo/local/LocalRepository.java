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

    Observable<List<Comment>> getAllCommentsByNoteId(long id);

    Observable<List<Description>> getDescriptionByNoteId(long id);

    Observable<List<Note>> getAllNotesOrderBy(Property property);
    Observable<Note> getNote(long id);
    Observable<Long> addNote(Note note);

    Observable<Long> UpdateNote(Note note);

    Observable<Boolean> deleteNote(long id);
}
