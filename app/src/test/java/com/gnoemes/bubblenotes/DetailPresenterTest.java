package com.gnoemes.bubblenotes;

import com.gnoemes.bubblenotes.repo.local.LocalRepositoryImpl;
import com.gnoemes.bubblenotes.repo.model.Note;
import com.gnoemes.bubblenotes.ui.note_detail.NoteDetailPresenter;
import com.gnoemes.bubblenotes.ui.note_detail.NoteDetailView$$State;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.File;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by kenji1947 on 29.09.2017.
 */

public class DetailPresenterTest {

    protected File boxStoreDir;

    private NoteDetailPresenter presenter;
    @Mock
    private NoteDetailView$$State viewState;
    @Mock
    LocalRepositoryImpl localRepositoryBox;
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        presenter = new NoteDetailPresenter(
                Schedulers.trampoline(),
                Schedulers.trampoline(),
                localRepositoryBox,
                1);
        presenter.setViewState(viewState);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void name() throws Exception {
        Note note = new Note();
        note.setId(1);
        note.setName("asd");
        note.setUnixTime(3);

        when(localRepositoryBox.getNote(1)).thenReturn(Observable.just(note));
        presenter.getNote(note.getId());
        verify(viewState, times(1)).setNote(note);

        Throwable t = new Throwable("error");

        //when(localRepositoryBox.getNote(1)).thenReturn(Observable.error(t));
        when(localRepositoryBox.getNote(1)).thenThrow(new RuntimeException());
        presenter.getNote(note.getId());
        verify(viewState, times(1)).showToast("Error " + new Throwable("error"));

    }
}
