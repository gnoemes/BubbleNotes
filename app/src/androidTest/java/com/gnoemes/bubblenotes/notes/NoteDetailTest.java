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
    private Realm realm;
    private NoteDetailPresenter presenter;
    @Mock
    NoteDetailView$$State stateView;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        RealmConfiguration config =
                new RealmConfiguration.Builder().inMemory().name("test-realm").build();

        InstrumentationRegistry.getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                realm = Realm.getInstance(config);
            }
        });

        presenter = new NoteDetailPresenter(null,"1");
        presenter.setViewState(stateView);
    }
    @After
    public void tearDown() throws Exception {
        InstrumentationRegistry.getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                realm.close();
            }
        });
    }

    @Test
    public void onFirstViewAttach_ShouldLoadNoteFromDbAndShow() throws Exception {

        InstrumentationRegistry.getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                realm.executeTransaction(realm1 -> {
                    Note note = realm1.createObject(Note.class, "1");
                    note.setName("n");
                    note.setPriority(2);
                });
            }
        });

        //Note note = Realm.getDefaultInstance().where(Note.class).equalTo("id", "1").findFirst();

        //Log.i("TEST", "note == null " + (note == null));

//        presenter.onFirstViewAttach();
        //verify(stateView, times(1)).setNote(note);
    }
}