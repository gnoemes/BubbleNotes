package com.gnoemes.bubblenotes.note.presenters;

import com.gnoemes.bubblenotes.data.model.Note;
import com.gnoemes.bubblenotes.data.source.NoteRepository;
import com.gnoemes.bubblenotes.ui.note_detail.NoteDetailPresenter;
import com.gnoemes.bubblenotes.ui.note_detail.NoteDetailView$$State;
import com.gnoemes.bubblenotes.utils.NoteMapper;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.Completable;
import io.reactivex.Single;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class NoteDetailPresenterTest {
    private NoteDetailPresenter presenter;

    @Mock
    NoteRepository repository;

    @Mock
    NoteDetailView$$State state;
    private Note note;


    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        presenter = new NoteDetailPresenter(repository,"1");
        presenter.setViewState(state);
        note = NoteMapper.createNoteFromData("1","name",1);
    }

    @Test
    public void setNote_ShouldShowNoteOnScreen() {
        when(repository.loadNoteById(note.getId())).thenReturn(Single.just(note));
        presenter.getNote(note.getId());
        verify(state,times(1)).setNote(note);
    }

    @Test
    public void deleteNote_ShouldShowToastAndBackPressed() {
        when(repository.deleteNote(note.getId())).thenReturn(Completable.complete());
        presenter.deleteNote(note.getId());
        verify(state,times(1)).showToast("Note deleted");
        verify(state,times(1)).backPressed();
    }

    @Test
    public void updateNote_ShouldShowToastAndBackPressed() {
//        when(repository.addOrUpdateNote(note)).thenReturn(Single.just(note));
//        presenter.updateNote(note.getId(),note.getName(),2);
//        verify(state,times(1)).showToast("Note updated");
//        verify(state,times(1)).backPressed();
    }
}
