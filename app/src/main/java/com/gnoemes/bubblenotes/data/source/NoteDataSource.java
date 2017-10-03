package com.gnoemes.bubblenotes.data.source;

import com.gnoemes.bubblenotes.data.model.Note;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

/**
 * Created by kenji1947 on 25.09.2017.
 */

public interface NoteDataSource {
    Flowable<Note> loadNoteById(long id);
    Flowable<List<Note>> loadNotes();
    Completable addOrUpdateNote(Note note);
    Completable deleteNote(long id);
}
