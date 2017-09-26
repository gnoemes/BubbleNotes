package com.gnoemes.bubblenotes.di;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppTestModule {

    @Provides
    @Singleton
    Context provideContext() {
        return InstrumentationRegistry.getTargetContext();
    }
}
