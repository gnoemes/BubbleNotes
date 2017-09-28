package com.gnoemes.bubblenotes.di.modules;

import com.gnoemes.bubblenotes.data.source.NoteDataSource;
import com.gnoemes.bubblenotes.data.source.NoteRepository;
import com.gnoemes.bubblenotes.di.annotations.Local;
import com.gnoemes.bubblenotes.di.annotations.Remote;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {

    @Provides
    @Singleton
    NoteRepository provideDataManager(@Local NoteDataSource localDataSource,
                                      @Remote NoteDataSource remoteDataSource) {
        return new NoteRepository(localDataSource,remoteDataSource);
    }
}
