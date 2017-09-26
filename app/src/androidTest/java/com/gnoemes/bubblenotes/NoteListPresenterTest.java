package com.gnoemes.bubblenotes;

import android.support.test.InstrumentationRegistry;

import com.gnoemes.bubblenotes.data.model.Note;
import com.gnoemes.bubblenotes.data.source.DataManager;
import com.gnoemes.bubblenotes.ui.note_detail.NoteDetailPresenter;
import com.gnoemes.bubblenotes.ui.notes_list.NotesListPresenter;
import com.gnoemes.bubblenotes.ui.notes_list.NotesListView$$State;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.Observable;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by kenji1947 on 27.09.2017.
 */

public class NoteListPresenterTest {
    @Mock
    NotesListView$$State viewState;
    @InjectMocks
    NotesListPresenter presenter;
    Realm realm;

    @Mock
    DataManager dataManager;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        RealmConfiguration config =
                new RealmConfiguration.Builder().inMemory().name("test-realm").build();
        realm = Realm.getInstance(config);

        presenter = new NotesListPresenter(dataManager);
        presenter.setViewState(viewState);

        realm.executeTransaction(realm1 -> {
            Note note = realm1.createObject(Note.class, "1");
            note.setName("");
            note.setPriority(1);
        });
    }

    @After
    public void tearDown() throws Exception {
        realm.close();
    }

    @Test
    public void firstTest() {
        RealmResults<Note> notes = realm.where(Note.class).findAll();
        when(dataManager.loadNotes()).thenReturn(Observable.just(notes));
        presenter.loadNotesRx();
        verify(viewState, times(1)).setNotesList(notes);
    }
}
