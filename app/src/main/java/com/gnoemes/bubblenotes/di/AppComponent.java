package com.gnoemes.bubblenotes.di;


import android.content.Context;

import com.gnoemes.bubblenotes.App;
import com.gnoemes.bubblenotes.data.Manager;
import com.gnoemes.bubblenotes.data.database.RealmDatabase;
import com.gnoemes.bubblenotes.data.database.RealmManager;
import com.gnoemes.bubblenotes.ui.notes_list.NotesListPresenter;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class,RealmModule.class,DataManagerModule.class})
public interface AppComponent {

    void inject(App app);
    void inject(RealmManager realmManager);
    void inject(RealmDatabase realmDatabase);
    void inject(NotesListPresenter notesListPresenter);

    Context getContext();
    RealmDatabase getRealmDatabase();
    Manager.DataManager getDataManager();
//    PrefsManager prefsManager();

}
