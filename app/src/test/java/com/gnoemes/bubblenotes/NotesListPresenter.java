package com.gnoemes.bubblenotes;

import com.gnoemes.bubblenotes.data.source.DataManager;
import com.gnoemes.bubblenotes.data.source.local.RealmManager;
import com.gnoemes.bubblenotes.ui.note_detail.NoteDetailPresenter;
import com.gnoemes.bubblenotes.ui.note_detail.NoteDetailView$$State;
import com.gnoemes.bubblenotes.ui.notes_list.NotesListView$$State;

import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * Created by kenji1947 on 26.09.2017.
 */

public class NotesListPresenter {
    @Mock
    NotesListView$$State viewState;

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
        presenter = new NotesListPresenter();

    }

    //TODO Will be called for every test?
    private void initTestData() {

    }
}
