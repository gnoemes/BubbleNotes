package com.gnoemes.bubblenotes.notes;

import com.gnoemes.bubblenotes.ui.note_detail.NoteDetailPresenter;
import com.gnoemes.bubblenotes.ui.note_detail.NoteDetailView$$State;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;



public class NoteDetailTest {
    private NoteDetailPresenter presenter;
    @Mock
    NoteDetailView$$State stateView;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);


//        presenter = new NoteDetailPresenter(null,"1");
        presenter.setViewState(stateView);
    }
    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void onFirstViewAttach_ShouldLoadNoteFromDbAndShow() throws Exception {

    }
}