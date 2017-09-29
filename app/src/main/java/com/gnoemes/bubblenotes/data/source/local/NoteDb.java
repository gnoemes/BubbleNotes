package com.gnoemes.bubblenotes.data.source.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.gnoemes.bubblenotes.data.model.Note;
import com.gnoemes.bubblenotes.data.model.NoteDao;

@Database(entities = Note.class, version = 1)
public abstract class NoteDb extends RoomDatabase{

    public abstract NoteDao noteDao();
}
