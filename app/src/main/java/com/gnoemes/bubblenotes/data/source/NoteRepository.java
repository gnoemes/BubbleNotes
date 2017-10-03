package com.gnoemes.bubblenotes.data.source;


import android.support.annotation.NonNull;

import com.gnoemes.bubblenotes.data.model.Note;
import com.gnoemes.bubblenotes.di.annotations.Local;
import com.gnoemes.bubblenotes.di.annotations.Remote;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;
import io.reactivex.Observable;

/**
 * NoteRepository which controls Database, API
 */

@Singleton
public class NoteRepository implements NoteDataSource {

    @NonNull
    private final NoteDataSource localDataSource;
    @NonNull
    private final NoteDataSource remoteDataSource;

    @Inject
    public NoteRepository(@NonNull @Local NoteDataSource localDataSource,
                          @NonNull @Remote NoteDataSource remoteDataSource) {
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
    }

    @Override
    public Flowable<Note> loadNoteById(long id) {
        return localDataSource.loadNoteById(id);
    }

    @Override
    public Flowable<List<Note>> loadNotes() {
        return localDataSource.loadNotes();
    }

    @Override
    public Observable<Boolean> addOrUpdateNote(Note note) {
        return localDataSource.addOrUpdateNote(note);
    }

    @Override
    public Observable<Boolean> deleteNote(long id) {
        return localDataSource.deleteNote(id);
    }
}
