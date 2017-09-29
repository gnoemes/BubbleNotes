package com.gnoemes.bubblenotes.di.modules;

import com.gnoemes.bubblenotes.data.model.NoteDao;
import com.gnoemes.bubblenotes.data.source.NoteDataSource;
import com.gnoemes.bubblenotes.data.source.local.NoteLocalDataSource;
import com.gnoemes.bubblenotes.di.annotations.Local;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class NoteLocalDataSourceModule {

    @Provides
    @Singleton
    @Local
    NoteDataSource provideLocalDataSource(NoteDao noteDao) {
        return new NoteLocalDataSource(noteDao);
    }
}
