package com.gnoemes.bubblenotes;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import com.gnoemes.bubblenotes.repo.local.LocalRepositoryImpl;
import com.gnoemes.bubblenotes.repo.model.MyObjectBox;
import com.gnoemes.bubblenotes.repo.model.Note;
import com.gnoemes.bubblenotes.ui.note_detail.NoteDetailPresenter;
import com.gnoemes.bubblenotes.ui.note_detail.NoteDetailView$$State;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.File;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.reactivex.schedulers.Schedulers;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by kenji1947 on 29.09.2017.
 */

public class DetailPresenterInstrTest {
    protected BoxStore boxStore;
    private Box<Note> noteBox;


    protected File boxStoreDir;
    Context context;

    private NoteDetailPresenter presenter;
    @Mock
    private NoteDetailView$$State viewState;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

//        File tempFile = File.createTempFile("object-store-test", "");
//        tempFile.delete();
//        boxStoreDir = tempFile;
//        store = MyObjectBox.builder().directory(boxStoreDir).build();

        context = ((App)InstrumentationRegistry.getContext().getApplicationContext()).getAppContext();

        //TODO Создает в первый раз или открывает существующую
        boxStore = MyObjectBox.builder().androidContext(context).name("test-db").build();
        presenter = new NoteDetailPresenter(Schedulers.trampoline(), Schedulers.trampoline(),
                new LocalRepositoryImpl(boxStore),
                1);

        noteBox = boxStore.boxFor(Note.class);
    }

    @After
    public void tearDown() throws Exception {
        if (boxStore != null) {
            boxStore.boxFor(Note.class).removeAll();
            boxStore.close();
            //boxStore.deleteAllFiles();
        }
    }

    @Test
    public void name() throws Exception {
        Note note = new Note();
        note.setName("asd");
        note.setPriority(3);
        noteBox.put(note);

        presenter.getNote(1);
        verify(viewState, times(1)).setNote(note);


    }
}
