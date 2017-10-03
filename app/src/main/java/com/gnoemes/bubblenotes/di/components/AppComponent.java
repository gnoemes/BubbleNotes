package com.gnoemes.bubblenotes.di.components;


import com.gnoemes.bubblenotes.App;
import com.gnoemes.bubblenotes.data.note.local.NoteDataSource;
import com.gnoemes.bubblenotes.di.modules.AppModule;
import com.gnoemes.bubblenotes.di.modules.DatabaseModule;
import com.gnoemes.bubblenotes.di.modules.RepositoryModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class,DatabaseModule.class,RepositoryModule.class})
public interface AppComponent {
    NoteDataSource getNoteRepository();
    void inject(App app);
}
