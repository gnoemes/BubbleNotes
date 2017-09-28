package com.gnoemes.bubblenotes.data.model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.gnoemes.bubblenotes.data.source.local.Config;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface NoteDao {
    @Query("SELECT * FROM " + Config.TABLE_NAME)
    Flowable<List<Note>> getAllNotes();

    @Query("SELECT * FROM " + Config.TABLE_NAME + " WHERE id == :id")
    Flowable<Note> getNoteById(String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Note note);

    @Query("DELETE FROM " + Config.TABLE_NAME + " WHERE id == :id")
    void deleteNoteById(String id);

    @Query("DELETE FROM " + Config.TABLE_NAME)
    void deleteAllNotes();
}
