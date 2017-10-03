package com.gnoemes.bubblenotes.note.model;

import com.gnoemes.bubblenotes.data.note.model.Note;
import com.gnoemes.bubblenotes.utils.RxUtil;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest(RxUtil.class)
public class NoteRepositoryTest {
//    private NoteRepository repository;

//    @Mock
//    NoteDataSource localDataSource;
//
//    @Mock
//    NoteDataSource remoteDataSource;
    Note note;

    @Before
    public void setup() {
//        MockitoAnnotations.initMocks(this);
//        RxTestUtil.mockRxSchedulers();
//        repository = new NoteRepository(localDataSource,remoteDataSource);
//        note = new Note();
//        note.setId(1);
//        note.setName("note");
//        note.setPriority(1);
    }

    @Test
    public void addNoteShouldSave() {
//        when(localDataSource.addOrUpdateNote(note)).thenReturn(Observable.just(true));
//        TestObserver<Boolean> testObserver = repository.addOrUpdateNote(note).test();
//        testObserver.assertNoErrors();
//        verify(localDataSource, times(1)).addOrUpdateNote(note);
    }

    @Test
    public void deleteNoteShouldTrue() {
//        when(localDataSource.deleteNote(note.getId())).thenReturn(Observable.just(note.getId() != -1));
//        TestObserver<Boolean> testObserver = repository.deleteNote(note.getId()).test();
//        testObserver.assertNoErrors();
//        testObserver.assertValue(true);
//        verify(localDataSource, times(1)).deleteNote(note.getId());
    }

    @Test
    public void loadNoteByIdShouldReturnExcepted() {
//        when(localDataSource.loadNoteById(note.getId())).thenReturn(Flowable.just(note));
//        Flowable<Note> testObserver = repository.loadNoteById(note.getId());
//        Note testNote = testObserver.blockingFirst();
//        assertEquals(testNote.getId(),note.getId());
//        assertEquals(testNote.getName(),note.getName());
//        assertEquals(testNote.getPriority(),note.getPriority());
//        verify(localDataSource, times(1)).loadNoteById(note.getId());
    }

    @Test
    public void loadNotesShouldReturnExcepted() {
//        Note note1 = NoteMapper.createNoteFromDataWithId(1,"name",1);
//        Note note2 = NoteMapper.createNoteFromDataWithId(2,"name",1);
//        List<Note> notes = new ArrayList<>();
//        notes.add(note1);
//        notes.add(note2);
//        when(localDataSource.loadNotes()).thenReturn(Flowable.just(notes));
//        Flowable<List<Note>> testObserver = repository.loadNotes();
//        List<Note> testNoteList = testObserver.blockingFirst();
//        assertTrue(testNoteList.size() == notes.size());
//        for (int i = 0; i < testNoteList.size(); i++) {
//            assertEquals(testNoteList.get(i),notes.get(i));
//        }
//        verify(localDataSource, times(1)).loadNotes();
    }
}
