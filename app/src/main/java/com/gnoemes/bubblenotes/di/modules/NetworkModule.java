package com.gnoemes.bubblenotes.di.modules;


import com.gnoemes.bubblenotes.data.source.NoteDataSource;
import com.gnoemes.bubblenotes.data.source.remote.RemoteDataSource;
import com.gnoemes.bubblenotes.di.annotations.Remote;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class NetworkModule {

    @Provides
    @Singleton
    @Remote
    NoteDataSource provideRemoteDataManager() {
        return new RemoteDataSource();
    }
}
