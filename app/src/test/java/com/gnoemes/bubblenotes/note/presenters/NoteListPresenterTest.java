package com.gnoemes.bubblenotes.note.presenters;

import com.gnoemes.bubblenotes.data.note.model.Note;
import com.gnoemes.bubblenotes.ui.notes_list.NotesListPresenter;
import com.gnoemes.bubblenotes.ui.notes_list.NotesListView$$State;
import com.gnoemes.bubblenotes.utils.RxUtil;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest(RxUtil.class)
public class NoteListPresenterTest {
    private NotesListPresenter presenter;

//    @Mock
//    NoteRepository repository;

    @Mock
    NotesListView$$State state;
    private Note note;


    @Before
    public void setup() {
//        MockitoAnnotations.initMocks(this);
//        RxTestUtil.mockRxSchedulers();
//        presenter = new NotesListPresenter(repository);
//        presenter.setViewState(state);
//        note = NoteMapper.createNoteFromDataWithId(1,"name",1);
    }

    @Test
    public void loadListShouldUpdate() {
//        Note note1 = NoteMapper.createNoteFromDataWithId(2,"name",1);
//        List<Note> notes = new ArrayList<>();
//        notes.add(note);
//        notes.add(note1);
//        when(repository.loadNotes()).thenReturn(Flowable.just(notes));
//        presenter.loadNotesRx();
//        verify(state,times(1)).setNotesList(notes);
    }

    @Test
    public void deleteNoteShouldShowSuccess() {
//        when(repository.deleteNote(note.getId())).thenReturn(Observable.just(true));
//        presenter.deleteNote(note.getId());
//        verify(state, times(1)).showToast("Note deleted");
    }

    @Test
    public void deleteNoteSouldShowError() {
//        when(repository.deleteNote(note.getId())).thenReturn(Observable.just(false));
//        presenter.deleteNote(note.getId());
//        verify(state, times(1)).showToast("Error");
    }
}
