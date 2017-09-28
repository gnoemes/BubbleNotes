package com.gnoemes.bubblenotes.di;


import android.content.Context;

import com.gnoemes.bubblenotes.App;
import com.gnoemes.bubblenotes.data.source.DataManager;
import com.gnoemes.bubblenotes.data.source.local.RealmDatabase;
import com.gnoemes.bubblenotes.data.source.local.RealmManagerDefault;
import com.gnoemes.bubblenotes.data.source.remote.RemoteManager;
import com.gnoemes.bubblenotes.repo.local.LocalRepository;
import com.gnoemes.bubblenotes.repo_box.LocalRepositoryBox;
import com.gnoemes.bubblenotes.ui.note_detail.NoteDetailActivity;
import com.gnoemes.bubblenotes.ui.note_detail.NoteDetailPresenter;
import com.gnoemes.bubblenotes.ui.notes_list.NotesListFragment;
import com.gnoemes.bubblenotes.ui.notes_list.NotesListPresenter;

import javax.inject.Singleton;

import dagger.Component;
import io.objectbox.BoxStore;

@Singleton
@Component(modules = {AppModule.class,RealmModule.class,
        DataManagerModule.class,NetworkModule.class,
        LocalRepositoryModule.class,
        BoxStoreModule.class})
public interface AppComponent {

    void inject(App app);
    void inject(RealmManagerDefault realmManager);
    void inject(RealmDatabase realmDatabase);
    void inject(NoteDetailActivity noteDetailActivity);
    void inject(NotesListFragment notesListFragment);
    void inject(NotesListPresenter notesListPresenter);
    void inject(NoteDetailPresenter noteDetailPresenter);
    void inject(com.gnoemes.bubblenotes.ui_box.notes_list.NotesListFragment notesListFragment);
    void inject(LocalRepositoryBox localRepositoryBox);
    void inject (com.gnoemes.bubblenotes.ui_box.notes_list.NotesListPresenter notesListPresenter);

    Context getContext();
    RealmDatabase getRealmDatabase();
    RemoteManager getRemoteDataManager();
    DataManager getDataManager();
    LocalRepository getLocalRepository();
    BoxStore getBoxStore();
//    PrefsManager prefsManager();

}
