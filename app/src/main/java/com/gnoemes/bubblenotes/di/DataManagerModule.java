package com.gnoemes.bubblenotes.di;

import com.gnoemes.bubblenotes.data.source.DataManager;
import com.gnoemes.bubblenotes.data.source.DataManagerDefault;
import com.gnoemes.bubblenotes.data.source.local.RealmManager;
import com.gnoemes.bubblenotes.data.source.remote.RemoteManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DataManagerModule {

    @Provides
    @Singleton
    DataManager provideDataManager(RealmManager realmManager, RemoteManager remoteDataManager) {
        return new DataManagerDefault(realmManager, remoteDataManager);
    }
}
