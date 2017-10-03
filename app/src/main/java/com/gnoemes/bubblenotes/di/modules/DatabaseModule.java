package com.gnoemes.bubblenotes.di.modules;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.gnoemes.bubblenotes.data.model.NoteDao;
import com.gnoemes.bubblenotes.data.source.local.Config;
import com.gnoemes.bubblenotes.data.source.local.NoteDb;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


@Module
public class DatabaseModule {
    private static final String DATABASE = "note_database";

    @Provides
    @Named(DATABASE)
    String provideDatabaseName() {
        return Config.DATABASE_NAME;
    }

    @Provides
    @Singleton
    NoteDb provideNoteDb(Context context, @Named(DATABASE) String databaseName) {
        return Room.databaseBuilder(context,NoteDb.class, databaseName)
                .fallbackToDestructiveMigration()
                .build();
    }

    @Provides
    @Singleton
    NoteDao provideNoteDao(NoteDb noteDb) {
        return noteDb.noteDao();
    }
}
