package com.gnoemes.bubblenotes.di;


import android.content.Context;

import com.gnoemes.bubblenotes.App;
import com.gnoemes.bubblenotes.data.source.Manager;
import com.gnoemes.bubblenotes.data.source.local.RealmDatabase;
import com.gnoemes.bubblenotes.data.source.local.RealmManager;
import com.gnoemes.bubblenotes.data.source.remote.IRemoteDataManager;
import com.gnoemes.bubblenotes.ui.notes_list.NotesListPresenter;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class,RealmModule.class,DataManagerModule.class,NetworkModule.class})
public interface AppComponent {

    void inject(App app);
    void inject(RealmManager realmManager);
    void inject(RealmDatabase realmDatabase);
    void inject(NotesListPresenter notesListPresenter);

    Context getContext();
    RealmDatabase getRealmDatabase();
    IRemoteDataManager getRemoteDataManager();
    Manager.DataManager getDataManager();
//    PrefsManager prefsManager();

}
