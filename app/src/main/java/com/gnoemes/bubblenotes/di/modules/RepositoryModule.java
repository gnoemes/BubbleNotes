package com.gnoemes.bubblenotes.di.modules;

import com.gnoemes.bubblenotes.data.note.local.NoteDataSource;
import com.gnoemes.bubblenotes.data.note.local.NoteLocalDataSource;
import com.gnoemes.bubblenotes.data.note.model.NoteDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {

    @Provides
    @Singleton
    NoteDataSource provideDataManager(NoteDao noteDao) {
        return new NoteLocalDataSource(noteDao);
    }
}
