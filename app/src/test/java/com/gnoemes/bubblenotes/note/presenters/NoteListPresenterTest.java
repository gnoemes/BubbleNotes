package com.gnoemes.bubblenotes.note.presenters;

import com.gnoemes.bubblenotes.data.model.Note;
import com.gnoemes.bubblenotes.data.source.NoteRepository;
import com.gnoemes.bubblenotes.ui.note_detail.NoteDetailPresenter;
import com.gnoemes.bubblenotes.ui.note_detail.NoteDetailView$$State;
import com.gnoemes.bubblenotes.utils.NoteMapper;

import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class NoteListPresenterTest {
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


}
