package com.gnoemes.bubblenotes.di.components;


import com.gnoemes.bubblenotes.App;
import com.gnoemes.bubblenotes.data.source.NoteRepository;
import com.gnoemes.bubblenotes.di.modules.AppModule;
import com.gnoemes.bubblenotes.di.modules.LocalDataSourceModule;
import com.gnoemes.bubblenotes.di.modules.NetworkModule;
import com.gnoemes.bubblenotes.di.modules.RepositoryModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class,LocalDataSourceModule.class,NetworkModule.class,RepositoryModule.class})
public interface AppComponent {
    NoteRepository getNoteRepository();
    void inject(App app);
}
