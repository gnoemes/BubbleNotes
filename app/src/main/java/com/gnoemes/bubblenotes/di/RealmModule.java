package com.gnoemes.bubblenotes.di;

import com.gnoemes.bubblenotes.data.Manager;
import com.gnoemes.bubblenotes.data.database.RealmDatabase;
import com.gnoemes.bubblenotes.data.database.RealmManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RealmModule {

    @Provides
    @Singleton
    RealmDatabase provideRealmDatabase() {
        return new RealmDatabase();
    }

    @Provides
    @Singleton
    Manager.RealmManager provideRealmManager() {
        return new RealmManager();

    }
}
