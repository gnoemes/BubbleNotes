package com.gnoemes.bubblenotes;

import android.app.Application;

import com.gnoemes.bubblenotes.di.AppComponent;
import com.gnoemes.bubblenotes.di.AppModule;
import com.gnoemes.bubblenotes.di.DaggerAppComponent;
import com.gnoemes.bubblenotes.di.DataManagerModule;
import com.gnoemes.bubblenotes.di.NetworkModule;
import com.gnoemes.bubblenotes.di.RealmModule;
import com.gnoemes.bubblenotes.data.source.local.RealmDatabase;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * Created by kenji1947 on 24.09.2017.
 */

public class App extends Application {

    @Inject
    RealmDatabase realmDatabase;
    private static AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

        initAppComponent(this);
        getAppComponent().inject(this);
        realmDatabase.setupRealm();
        realmDatabase.setupStetho();

    }

    public static AppComponent getAppComponent() {
        return appComponent;
    }

    private void initAppComponent(App app) {
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(app))
                .realmModule(new RealmModule())
                .dataManagerModule(new DataManagerModule())
                .networkModule(new NetworkModule())
                .build();
    }

    public AppComponent getComponent() {
        if (appComponent == null) {
            appComponent = DaggerAppComponent.builder()
                    .appModule(new AppModule(this))
                    .realmModule(new RealmModule())
                    .dataManagerModule(new DataManagerModule())
                    .networkModule(new NetworkModule())
                    .build();
        }
        return appComponent;
    }

}
