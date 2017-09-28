package com.gnoemes.bubblenotes.di;

import com.gnoemes.bubblenotes.repo.local.LocalRepository;
import com.gnoemes.bubblenotes.repo.local.LocalRepositoryImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by kenji1947 on 28.09.2017.
 */
@Module
public class LocalRepositoryModule {

    @Provides
    @Singleton
    LocalRepository provideLocalRepository() {
        return new LocalRepositoryImpl();
    }
}
