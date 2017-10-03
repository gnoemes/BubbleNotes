package com.gnoemes.bubblenotes.data.source.local;

import com.gnoemes.bubblenotes.data.model.Note;
import com.gnoemes.bubblenotes.data.model.NoteDao;
import com.gnoemes.bubblenotes.data.source.NoteDataSource;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Flowable;

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
    public Completable addOrUpdateNote(Note note) {
        return Completable.fromAction(() -> noteDao.insert(note));
    }

    public Completable deleteNote(long id) {
        return Completable.fromAction(() -> noteDao.deleteNoteById(id));
    }
}
