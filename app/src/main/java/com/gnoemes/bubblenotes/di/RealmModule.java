package com.gnoemes.bubblenotes.di;

import com.gnoemes.bubblenotes.data.source.local.RealmDatabase;
import com.gnoemes.bubblenotes.data.source.local.RealmManager;
import com.gnoemes.bubblenotes.data.source.local.RealmManagerDefault;

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
    RealmManager provideRealmManager() {
        return new RealmManagerDefault();

    }
}
