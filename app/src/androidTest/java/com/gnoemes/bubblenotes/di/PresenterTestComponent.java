package com.gnoemes.bubblenotes.di;

import com.gnoemes.bubblenotes.notes.NoteListPresenterTest;

import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(modules = {AppTestModule.class,RealmModule.class, NetworkModule.class, DataManagerModule.class})
public interface PresenterTestComponent extends AppComponent{
        void inject(NoteListPresenterTest noteListPresenterTest);
}
