package com.gnoemes.bubblenotes.di;

import com.gnoemes.bubblenotes.data.source.DataManager;
import com.gnoemes.bubblenotes.data.source.Manager;
import com.gnoemes.bubblenotes.data.source.remote.IRemoteDataManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DataManagerModule {

    @Provides
    @Singleton
    Manager.DataManager provideDataManager(Manager.RealmManager realmManager, IRemoteDataManager remoteDataManager) {
        return new DataManager(realmManager, remoteDataManager);
    }
}
