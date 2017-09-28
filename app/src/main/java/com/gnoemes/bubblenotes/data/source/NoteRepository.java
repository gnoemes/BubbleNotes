package com.gnoemes.bubblenotes.data.source;


import android.support.annotation.NonNull;

import com.gnoemes.bubblenotes.data.model.Note;
import com.gnoemes.bubblenotes.di.annotations.Local;
import com.gnoemes.bubblenotes.di.annotations.Remote;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Flowable;

/**
 * NoteRepository which controls Realm Database, API
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
    public Flowable<Note> loadNoteById(String id) {
        return localDataSource.loadNoteById(id);
    }

    @Override
    public Flowable<List<Note>> loadNotes() {
        return localDataSource.loadNotes()
                .flatMapIterable(notes -> notes)
                .toSortedList((t1, t2) -> {
                    if (t1.getPriority() > t2.getPriority()) {
                        return 1;
                    }
                    if (t1.getPriority() < t2.getPriority()) {
                        return -1;
                    }
                    return 0;
                })
                .toFlowable();




    }

    @Override
    public Completable addOrUpdateNote(Note note) {
        return localDataSource.addOrUpdateNote(note);
    }

    @Override
    public Completable deleteNote(String id) {
        return localDataSource.deleteNote(id);
    }
}
