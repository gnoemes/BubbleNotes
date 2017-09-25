package com.gnoemes.bubblenotes.di;


import com.gnoemes.bubblenotes.data.source.remote.RemoteManager;
import com.gnoemes.bubblenotes.data.source.remote.RemoteManagerDefault;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class NetworkModule {

    @Provides
    @Singleton
    RemoteManager provideRemoteDataManager() {
        return new RemoteManagerDefault();
}
}
