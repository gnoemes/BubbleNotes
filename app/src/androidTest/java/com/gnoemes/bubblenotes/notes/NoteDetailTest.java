package com.gnoemes.bubblenotes;

import android.support.test.InstrumentationRegistry;
import android.util.Log;

import com.gnoemes.bubblenotes.data.model.Note;
import com.gnoemes.bubblenotes.ui.note_detail.NoteDetailPresenter;
import com.gnoemes.bubblenotes.ui.note_detail.NoteDetailView$$State;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.realm.Realm;
import io.realm.RealmConfiguration;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;



public class NoteDetailTest {
    private NoteDetailPresenter presenter;
    @Mock
    NoteDetailView$$State stateView;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);


        presenter = new NoteDetailPresenter(null,"1");
        presenter.setViewState(stateView);
    }
    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void onFirstViewAttach_ShouldLoadNoteFromDbAndShow() throws Exception {

    }
}