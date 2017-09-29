package com.gnoemes.bubblenotes.di.components;


import com.gnoemes.bubblenotes.App;
import com.gnoemes.bubblenotes.data.source.NoteRepository;
import com.gnoemes.bubblenotes.di.modules.AppModule;
import com.gnoemes.bubblenotes.di.modules.DatabaseModule;
import com.gnoemes.bubblenotes.di.modules.NetworkModule;
import com.gnoemes.bubblenotes.di.modules.NoteLocalDataSourceModule;
import com.gnoemes.bubblenotes.di.modules.RepositoryModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class,DatabaseModule.class,NetworkModule.class,RepositoryModule.class, NoteLocalDataSourceModule.class})
public interface AppComponent {
    NoteRepository getNoteRepository();
    void inject(App app);
}
