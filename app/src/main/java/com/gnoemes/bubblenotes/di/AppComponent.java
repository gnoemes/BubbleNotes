package com.gnoemes.bubblenotes.di;


import android.content.Context;

import com.gnoemes.bubblenotes.App;
import com.gnoemes.bubblenotes.repo.local.LocalRepositoryImpl;


import javax.inject.Singleton;

import dagger.Component;
import io.objectbox.BoxStore;

@Singleton
@Component(modules = {AppModule.class,
        BoxStoreModule.class})
public interface AppComponent {

    void inject(App app);
    void inject(com.gnoemes.bubblenotes.ui.notes_list.NotesListFragment notesListFragment);
    void inject(LocalRepositoryImpl localRepositoryBox);
    void inject (com.gnoemes.bubblenotes.ui.notes_list.NotesListPresenter notesListPresenter);

    Context getContext();
    BoxStore getBoxStore();
}
