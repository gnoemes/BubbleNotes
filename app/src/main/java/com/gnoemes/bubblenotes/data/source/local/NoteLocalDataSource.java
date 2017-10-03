package com.gnoemes.bubblenotes.data.source.local;

import com.gnoemes.bubblenotes.data.model.Note;
import com.gnoemes.bubblenotes.data.model.NoteDao;
import com.gnoemes.bubblenotes.data.source.NoteDataSource;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Observable;

public class NoteLocalDataSource implements NoteDataSource {

    private NoteDao noteDao;

    @Inject
    public NoteLocalDataSource(NoteDao noteDao) {
        this.noteDao = noteDao;
    }

    @Override
    public Flowable<Note> loadNoteById(long id) {
        return noteDao.getNoteById(id);
    }

    @Override
    public Flowable<List<Note>> loadNotes() {
        return noteDao.getAllNotes();
    }

    @Override
    public Observable<Boolean> addOrUpdateNote(Note note) {
        return Observable.fromCallable(() -> noteDao.insert(note) != -1);
    }

    public Observable<Boolean> deleteNote(long id) {
        return Observable.fromCallable(() -> noteDao.deleteNoteById(id) != -1);
    }
}
