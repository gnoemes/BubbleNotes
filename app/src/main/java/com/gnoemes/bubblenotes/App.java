package com.gnoemes.bubblenotes;

import android.app.Application;

import com.gnoemes.bubblenotes.di.components.AppComponent;
import com.gnoemes.bubblenotes.di.components.DaggerAppComponent;
import com.gnoemes.bubblenotes.di.modules.AppModule;
import com.gnoemes.bubblenotes.di.modules.LocalDataSourceModule;
import com.gnoemes.bubblenotes.di.modules.NetworkModule;

import timber.log.Timber;

/**
 * Created by kenji1947 on 24.09.2017.
 */

public class App extends Application {

    private static AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

        initAppComponent(this);
        getAppComponent().inject(this);

    }

    public static AppComponent getAppComponent() {
        return appComponent;
    }

    private void initAppComponent(App app) {
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(app))
                .localDataSourceModule(new LocalDataSourceModule())
                .networkModule(new NetworkModule())
                .build();
    }
}
