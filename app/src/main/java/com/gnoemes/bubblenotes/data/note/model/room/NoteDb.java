package com.gnoemes.bubblenotes.data.note.model.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.gnoemes.bubblenotes.data.note.model.Note;
import com.gnoemes.bubblenotes.data.note.model.NoteDao;

@Database(entities = Note.class, version = 3)
public abstract class NoteDb extends RoomDatabase{

    public abstract NoteDao noteDao();
}
