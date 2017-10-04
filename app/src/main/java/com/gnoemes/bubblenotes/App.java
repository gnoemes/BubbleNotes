package com.gnoemes.bubblenotes;

import android.app.Application;
import android.content.Context;


import com.gnoemes.bubblenotes.repo.model.Comment;
import com.gnoemes.bubblenotes.repo.model.Description;
import com.gnoemes.bubblenotes.repo.model.MyObjectBox;
import com.gnoemes.bubblenotes.repo.model.Note;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.android.AndroidObjectBrowser;
import timber.log.Timber;

/**
 * Created by kenji1947 on 24.09.2017.
 */

public class App extends Application {
    private Context context;
    private BoxStore boxStore;

    private Box<Note> noteBox;
    private Box<Comment> commentBox;
    private Box<Description> descriptionBox;

    @Override
    public void onCreate() {
        super.onCreate();
        boxStore = MyObjectBox.builder().androidContext(App.this).build();

        if (BuildConfig.DEBUG) {
            new AndroidObjectBrowser(boxStore).start(this);
            Timber.plant(new Timber.DebugTree());
        }
        context = this;
    }

    public void clearAllEntities() {
        noteBox = boxStore.boxFor(Note.class);
        commentBox = boxStore.boxFor(Comment.class);
        descriptionBox = boxStore.boxFor(Description.class);

        noteBox.removeAll();
        commentBox.removeAll();
        descriptionBox.removeAll();
    }

    public Context getAppContext() {
        return context;
    }

    public BoxStore getBoxStore() {
        return boxStore;
    }

}
