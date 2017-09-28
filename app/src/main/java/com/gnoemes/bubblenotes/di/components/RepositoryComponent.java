package com.gnoemes.bubblenotes.di.components;


import com.gnoemes.bubblenotes.di.annotations.ActivityScope;
import com.gnoemes.bubblenotes.ui.note_detail.NoteDetailPresenter;
import com.gnoemes.bubblenotes.ui.notes_list.NotesListPresenter;

import dagger.Component;

@ActivityScope
@Component(dependencies = AppComponent.class)
public interface RepositoryComponent {

    void inject(NoteDetailPresenter detailPresenter);
    void inject(NotesListPresenter listPresenter);
}
