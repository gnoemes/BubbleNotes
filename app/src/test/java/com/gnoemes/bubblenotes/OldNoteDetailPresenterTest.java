package com.gnoemes.bubblenotes;

import android.app.Instrumentation;

import com.gnoemes.bubblenotes.data.model.Note;
import com.gnoemes.bubblenotes.data.source.DataManager;
import com.gnoemes.bubblenotes.data.source.local.RealmManager;
import com.gnoemes.bubblenotes.ui.note_detail.NoteDetailPresenter;
import com.gnoemes.bubblenotes.ui.note_detail.NoteDetailView$$State;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoRule;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmResults;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by kenji1947 on 26.09.2017.
 */

public class OldNoteDetailPresenterTest {
    @Mock
    NoteDetailView$$State detailView$$State;
    @Mock
    RealmManager realmManager;
    @Mock
    DataManager dataManager;
    @InjectMocks
    NoteDetailPresenter presenter;

    Note note = new Note();
    RealmResults<Note> notes;
    static Realm testRealm;
    RealmList<Note> list;


    @BeforeClass
    public static void beforeSetup() {
//        RealmConfiguration testConfig =
//                new RealmConfiguration.Builder().
//                        inMemory().
//                        name("test-realm").build();
//        testRealm = Realm.getInstance(testConfig);
    }
    @AfterClass
    public static void afterTearDown() {
//        testRealm.close();
    }


    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        initTestData();

        //TODO Inject Schedulers in Presenter. Schedulers.immediate()
        presenter = new NoteDetailPresenter(null, null, null, dataManager, "1");
        presenter.setViewState(detailView$$State);
    }

    //TODO Will be called for every test?
    private void initTestData() {
        note.setName("name");
        note.setPriority(1);
        note.setId("1");
    }

    @Ignore
    public void onFirstViewAttach_ShouldShowToastAndPressBack() {
        String id = "1";
        Note note = new Note();

        when(dataManager.loadNotes(id)).thenReturn(Observable.just(note));
        presenter.getNote("1");
        verify(detailView$$State, times(1)).setNote(note);
    }

    @Ignore
    public void deleteNoteTest_ShouldShowToastAndPressBack() {
        when(dataManager.deleteNote("1")).thenReturn(Observable.just(true));
        presenter.deleteNote("1");
        verify(detailView$$State, times(1)).showToast("Note deleted");
        verify(detailView$$State, times(1)).backPressed();
    }


    @Ignore
    public void addNoteTest_ShouldShowToastAndPressBack() {

        when(dataManager.addNote("name", 1)).thenReturn(Observable.just("1"));
        presenter.addNote("1", "name", 1);
        verify(detailView$$State, times(1)).showToast("Note saved " + note.getId());
        verify(detailView$$State, times(1)).backPressed();
    }

    @Ignore
    public void updateNoteTest_ShouldShowToast() {
        String id = "1";
        String name = "m";
        int p = 1;

        when(dataManager.updateNote(id, name, p)).thenReturn(Observable.just(true));
        presenter.updateNote(id, name, p);

        verify(detailView$$State, times(1)).showToast("Note updated");
        verify(detailView$$State, times(1)).backPressed();
    }


}
