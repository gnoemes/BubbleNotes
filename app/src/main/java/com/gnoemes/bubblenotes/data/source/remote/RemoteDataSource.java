package com.gnoemes.bubblenotes.data.source.remote;

import com.gnoemes.bubblenotes.data.model.Note;
import com.gnoemes.bubblenotes.data.source.NoteDataSource;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;

public class RemoteDataSource implements NoteDataSource {

    @Override
    public Flowable<Note> loadNoteById(long id) {
        throw  new UnsupportedOperationException();
    }

    @Override
    public Flowable<List<Note>> loadNotes() {
        throw  new UnsupportedOperationException();
    }

    @Override
    public Observable<Boolean> addOrUpdateNote(Note note) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Observable<Boolean> deleteNote(long id) {
        throw  new UnsupportedOperationException();
    }
}
