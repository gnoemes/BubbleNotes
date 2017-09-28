package com.gnoemes.bubblenotes.di.modules;

import android.content.Context;

import com.gnoemes.bubblenotes.data.source.NoteDataSource;
import com.gnoemes.bubblenotes.data.source.local.NoteLocalDataSource;
import com.gnoemes.bubblenotes.di.annotations.Local;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import timber.log.Timber;

@Module
public class LocalDataSourceModule {

    @Provides
    @Singleton
    Realm provideRealm(RealmConfiguration config) {
        Realm.setDefaultConfiguration(config);
        try {
            return Realm.getDefaultInstance();
        } catch (Exception e) {
            Timber.e(e, "");
            Realm.deleteRealm(config);
            Realm.setDefaultConfiguration(config);
            return Realm.getDefaultInstance();
        }
    }
    @Provides
    @Singleton
    RealmConfiguration provideRealmConfig(Context context) {
        Realm.init(context);
        return new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        }

    @Provides
    @Singleton
    @Local
    NoteDataSource provideRealmManager(Realm realm) {
        return new NoteLocalDataSource(realm);
    }
}
