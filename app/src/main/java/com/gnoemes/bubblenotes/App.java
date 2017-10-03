package com.gnoemes.bubblenotes;

import android.app.Application;
import android.content.Context;

import com.amitshekhar.ObjectBoxBrowser;
import com.gnoemes.bubblenotes.di.AppComponent;
import com.gnoemes.bubblenotes.di.AppModule;
import com.gnoemes.bubblenotes.di.DaggerAppComponent;
import com.gnoemes.bubblenotes.repo.model.Comment;
import com.gnoemes.bubblenotes.repo.model.Description;
import com.gnoemes.bubblenotes.repo.model.MyObjectBox;
import com.gnoemes.bubblenotes.repo.model.Note;

import javax.inject.Inject;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import timber.log.Timber;

/**
 * Created by kenji1947 on 24.09.2017.
 */

public class App extends Application {
    private static AppComponent appComponent;
    private static Context context;

    BoxStore boxStore;

    private Box<Note> noteBox;
    private Box<Comment> commentBox;
    private Box<Description> descriptionBox;


    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
        boxStore = MyObjectBox.builder().androidContext(App.this).build();
        ObjectBoxBrowser.setBoxStore(boxStore);
//        ObjectBoxBrowser.showDebugDBAddressLogToast(App.this);

        //initAppComponent(this);
        //getAppComponent().inject(this);
    }
    public void clearAllEntities() {
        noteBox = boxStore.boxFor(Note.class);
        commentBox = boxStore.boxFor(Comment.class);
        descriptionBox = boxStore.boxFor(Description.class);

        noteBox.removeAll();
        commentBox.removeAll();;
        descriptionBox.removeAll();
    }
    public static Context getAppContext() {
        return context;
    }
   public BoxStore getBoxStore() {
        return boxStore;
    }

    public static AppComponent getAppComponent() {
        return appComponent;
    }

    private void initAppComponent(App app) {
        appComponent = DaggerAppComponent.builder()
                //TODO change
                .appModule(new AppModule(this))
                //.boxStoreModule(new BoxStoreModule())
                .build();
    }

    public AppComponent getComponent() {
        if (appComponent == null) {
            appComponent = DaggerAppComponent.builder()
                    .appModule(new AppModule(this))
                    .build();
        }
        return appComponent;
    }

}
