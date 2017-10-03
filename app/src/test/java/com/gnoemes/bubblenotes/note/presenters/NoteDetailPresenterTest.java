package com.gnoemes.bubblenotes.note.presenters;

import com.gnoemes.bubblenotes.data.note.model.Note;
import com.gnoemes.bubblenotes.ui.note_detail.NoteDetailPresenter;
import com.gnoemes.bubblenotes.ui.note_detail.NoteDetailView$$State;
import com.gnoemes.bubblenotes.utils.RxUtil;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest(RxUtil.class)
public class NoteDetailPresenterTest {
    private NoteDetailPresenter presenter;

//    @Mock
//    NoteRepository repository;

    @Mock
    NoteDetailView$$State state;
    private Note note;


    @Before
    public void setup() {
//        MockitoAnnotations.initMocks(this);
//        RxTestUtil.mockRxSchedulers();
//        presenter = new NoteDetailPresenter(repository,1);
//        presenter.setViewState(state);
//        note = NoteMapper.createNoteFromDataWithId(1,"name",1);
    }

    @Test
    public void setNote_ShouldShowNoteOnScreen() {
//        when(repository.loadNoteById(note.getId())).thenReturn(Flowable.just(note));
//        presenter.getNote(note.getId());
//        verify(state,times(1)).setNote(note);
    }

    @Test
    public void deleteNote_ShouldSuccessAndShowToastAndBackPressed() {
//        when(repository.deleteNote(note.getId())).thenReturn(Observable.just(true));
//        presenter.deleteNote(note.getId());
//        verify(state,times(1)).showToast("Note deleted");
//        verify(state,times(1)).backPressed();
    }

    @Test
    public void deleteNote_ShouldFailureAndShowToast() {
//        when(repository.deleteNote(note.getId())).thenReturn(Observable.just(false));
//        presenter.deleteNote(note.getId());
//        verify(state,times(1)).showToast("Error");
    }

    @Test
    public void addNote_ShouldShowToastAndBackPressed() {
//        Note note1 = NoteMapper.createNoteFromData(note.getName(),note.getPriority());
//        when(repository.addOrUpdateNote(note1)).thenReturn(Observable.just(true));
//        presenter.addNote(note1.getName(),note1.getPriority());
//        verify(state,times(1)).showToast("Note added");
//        verify(state,times(1)).backPressed();
    }
}
