package com.gnoemes.bubblenotes.note.model;

import com.gnoemes.bubblenotes.data.model.Note;
import com.gnoemes.bubblenotes.data.source.NoteDataSource;
import com.gnoemes.bubblenotes.data.source.NoteRepository;
import com.gnoemes.bubblenotes.util.RxTestUtil;
import com.gnoemes.bubblenotes.utils.NoteMapper;
import com.gnoemes.bubblenotes.utils.RxUtil;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import io.reactivex.Single;
import io.reactivex.observers.TestObserver;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(RxUtil.class)
public class NoteRepositoryTest {
    private NoteRepository repository;

    @Mock
    NoteDataSource localDataSource;

    @Mock
    NoteDataSource remoteDataSource;


    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RxTestUtil.mockRxSchedulers();
        repository = new NoteRepository(localDataSource,remoteDataSource);
    }

    @Test
    public void addNoteShouldSave() {
        Note note = NoteMapper.createNoteFromData("1","note",1);
        when(localDataSource.addOrUpdateNote(note)).thenReturn(Single.just(note));
        TestObserver<Note> testObserver = repository.addOrUpdateNote(note).test();
        testObserver.assertNoErrors();
        verify(localDataSource, times(1)).addOrUpdateNote(note);
    }

}
