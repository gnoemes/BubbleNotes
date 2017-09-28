package com.gnoemes.bubblenotes.data.source;


import android.support.annotation.NonNull;

import com.gnoemes.bubblenotes.data.model.Note;
import com.gnoemes.bubblenotes.di.annotations.Local;
import com.gnoemes.bubblenotes.di.annotations.Remote;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.realm.RealmResults;
import io.realm.rx.CollectionChange;

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
    public Single<Note> loadNoteById(String id) {
        return localDataSource.loadNoteById(id);
    }

    @Override
    public Observable<CollectionChange<RealmResults<Note>>> loadNotes() {
        return localDataSource.loadNotes();
    }

    @Override
    public Single<Note> addOrUpdateNote(Note note) {
        return localDataSource.addOrUpdateNote(note);
    }

    @Override
    public Completable deleteNote(String id) {
        return localDataSource.deleteNote(id);
    }
}
