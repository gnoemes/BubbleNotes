package com.gnoemes.bubblenotes;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import timber.log.Timber;

/**
 * Created by kenji1947 on 24.09.2017.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

        // Initialize Realm. Should only be done once when the application starts.
        Realm.init(this);

        //TODO Take migration from other project. If db schema was changed, all data will be deleted!
        RealmConfiguration config = new RealmConfiguration
                .Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);

        //TODO Stetho is not working. Wait for new version.
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                        .build());


        //clearRealmDb();
    }

    private void clearRealmDb() {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        Realm.getDefaultInstance().deleteAll();
        realm.commitTransaction();
    }
}
