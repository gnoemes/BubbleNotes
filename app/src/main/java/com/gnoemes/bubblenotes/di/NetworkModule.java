package com.gnoemes.bubblenotes.di;


import com.gnoemes.bubblenotes.data.source.remote.IRemoteDataManager;
import com.gnoemes.bubblenotes.data.source.remote.RemoteDataSource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class NetworkModule {

    @Provides
    @Singleton
    IRemoteDataManager provideRemoteDataManager() {
        return new RemoteDataSource();
}
}
