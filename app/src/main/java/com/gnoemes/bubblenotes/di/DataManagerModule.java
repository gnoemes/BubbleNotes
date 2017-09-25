package com.gnoemes.bubblenotes.di;

import com.gnoemes.bubblenotes.data.DataManager;
import com.gnoemes.bubblenotes.data.Manager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DataManagerModule {

    @Provides
    @Singleton
    Manager.DataManager provideDataManager(Manager.RealmManager realmManager) {
        return new DataManager(realmManager);
    }
}
