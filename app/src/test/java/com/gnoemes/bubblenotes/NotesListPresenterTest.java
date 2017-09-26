package com.gnoemes.bubblenotes;

import com.gnoemes.bubblenotes.data.model.Note;
import com.gnoemes.bubblenotes.data.source.DataManager;
import com.gnoemes.bubblenotes.data.source.local.RealmManager;
import com.gnoemes.bubblenotes.ui.notes_list.NotesListPresenter;
import com.gnoemes.bubblenotes.ui.notes_list.NotesListView$$State;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.realm.RealmResults;
import io.realm.rx.CollectionChange;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by kenji1947 on 26.09.2017.
 */

public class NotesListPresenterTest {
    @Mock
    NotesListView$$State viewState;
    @InjectMocks
    NotesListPresenter presenter;
    @Mock
    RealmManager realmManager;
    @Mock
    DataManager dataManager;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        initTestData();

        //TODO Inject Schedulers in Presenter. Schedulers.immediate()
        presenter = new NotesListPresenter(dataManager);
        presenter.setViewState(viewState);

    }

    //TODO Will be called for every test?
    private void initTestData() {

    }
    @Test
    public void firstTest() {

        //CollectionChange<RealmResults<Note>> cc = new CollectionChange<>();

        RealmResults<Note> realmResults = mock(RealmResults.class);
        doNothing().when(realmResults);

        when(dataManager.loadNotes()).thenReturn(Observable.just(realmResults));
        presenter.loadNotesRx();
        verify(viewState, times(1)).setNotesList(realmResults);
    }
}
