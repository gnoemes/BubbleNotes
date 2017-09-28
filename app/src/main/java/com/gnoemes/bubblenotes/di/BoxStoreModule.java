package com.gnoemes.bubblenotes.di;

import android.content.Context;

import com.amitshekhar.ObjectBoxBrowser;
import com.gnoemes.bubblenotes.repo_box.model.MyObjectBox;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.objectbox.BoxStore;

/**
 * Created by kenji1947 on 28.09.2017.
 */
@Module
public class BoxStoreModule {

    @Provides
    @Singleton
    BoxStore provideBoxStore(Context context) {
        //BoxStore boxStore = MyObjectBox.builder().androidContext(context).build();
        //ObjectBoxBrowser.setBoxStore(boxStore);
        return null;
    }
}
