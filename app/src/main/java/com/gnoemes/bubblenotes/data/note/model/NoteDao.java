package com.gnoemes.bubblenotes.data.note.model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.gnoemes.bubblenotes.data.note.model.room.Config;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface NoteDao {
    @Query("SELECT * FROM " + Config.TABLE_NAME + " ORDER by "+Note.SORT_COMPLETE+" ASC, " + Note.SORT_PRIORITY + " ASC")
    Flowable<List<Note>> getAllNotes();

    @Query("SELECT * FROM " + Config.TABLE_NAME + " WHERE id == :id")
    Flowable<Note> getNoteById(long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Note note);

    @Query("DELETE FROM " + Config.TABLE_NAME + " WHERE id == :id")
    int deleteNoteById(long id);

    @Query("DELETE FROM " + Config.TABLE_NAME)
    void deleteAllNotes();
}
