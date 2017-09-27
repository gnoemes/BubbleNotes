package com.gnoemes.bubblenotes;

import android.support.annotation.UiThread;
import android.support.test.InstrumentationRegistry;
import android.support.test.annotation.UiThreadTest;
import android.support.test.rule.UiThreadTestRule;

import com.gnoemes.bubblenotes.data.model.Note;
import com.gnoemes.bubblenotes.data.source.DataManager;
import com.gnoemes.bubblenotes.data.source.DataManagerDefault;
import com.gnoemes.bubblenotes.ui.note_detail.NoteDetailPresenter;
import com.gnoemes.bubblenotes.ui.notes_list.NotesListPresenter;
import com.gnoemes.bubblenotes.ui.notes_list.NotesListView$$State;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

import io.reactivex.schedulers.Schedulers;
import io.realm.OrderedCollectionChangeSet;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by kenji1947 on 27.09.2017.
 */
@UiThread
public class NotesListPresenterTest {
    @Mock
    NotesListView$$State viewState;
    @InjectMocks
    NotesListPresenter presenter;
    Realm realm;

    @Captor
    ArgumentCaptor<OrderedCollectionChangeSet> changeSetArgumentCaptor;

    @Mock
    DataManager dataManager;
    @Rule
    public UiThreadTestRule uiThreadTestRule = new UiThreadTestRule();

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        RealmConfiguration config =
                new RealmConfiguration.Builder().inMemory().name("test-realm").build();
        realm = Realm.getInstance(config);

        //TODO Объяснить trampoline()
        presenter = new NotesListPresenter(realm,
                Schedulers.trampoline(),
                Schedulers.trampoline(),
                new DataManagerDefault(null, null));

        presenter.setViewState(viewState);

        realm.executeTransaction(realm1 -> {
            Note note = realm1.createObject(Note.class, "1");
            note.setName("");
            note.setPriority(1);
        });
    }

    @After
    public void tearDown() throws Exception {
        realm.close();
    }

    @UiThreadTest
    @Test
    public void loadNotesRx() {
        RealmResults<Note> list = realm.where(Note.class).findAll();
        presenter.loadNotesRx();
        realm.executeTransaction(realm1 -> {
            Note note = realm1.createObject(Note.class, "2");
            note.setName("");
            note.setPriority(2);
        });
        verify(viewState, times(1)).setNotesList(list);
        verify(viewState, times(2)).setChangeSet(changeSetArgumentCaptor.capture());
    }

    //Do not use
    public void oldMethodTest() {
        RealmResults<Note> list = realm.where(Note.class).findAll();
        //when(dataManager.loadNotes()).thenReturn(Observable.just(list));
        presenter.loadNotesRx();
        //verify(viewState, times(1)).setNotesList(list);

        realm.executeTransaction(realm1 -> {
            Note note = realm1.createObject(Note.class, "2");
            note.setName("");
            note.setPriority(2);
        });

        //Wait for notify from realm
//        try {
//            TimeUnit.MILLISECONDS.sleep(1);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        verify(viewState, times(1)).setNotesList(list);
        verify(viewState, times(2)).setChangeSet(changeSetArgumentCaptor.capture());
    }

    public void oldMethods() {

        //        RealmResults<Note> notes = realm.where(Note.class).findAll();
//        when(dataManager.loadNotes()).thenReturn(Observable.just(notes));
//        presenter.loadNotesRx();
//        verify(viewState, times(1)).setNotesList(notes);
//
//        RealmConfiguration config =
//                new RealmConfiguration.Builder().inMemory().name("test-realm").build();
//        Realm r = Realm.getInstance(config);
//        r.executeTransaction(realm1 -> {
//            Note note = realm1.createObject(Note.class, "2");
//            note.setName("");
//            note.setPriority(1);
//        });


        //r.close();
//        InstrumentationRegistry.getInstrumentation().runOnMainSync(new Runnable() {
//            @Override
//            public void run() {
//                RealmConfiguration config =
//                        new RealmConfiguration.Builder().inMemory().name("test-realm").build();
//                Realm r = Realm.getInstance(config);
//                r.executeTransaction(realm1 -> {
//                    Note note = realm1.createObject(Note.class, "2");
//                    note.setName("");
//                    note.setPriority(1);
//                });
//
//                RealmResults<Note> list = r.where(Note.class).findAll();
//                when(dataManager.loadNotes()).thenReturn(Observable.just(list));
//                presenter.loadNotesRx();
//                verify(viewState, times(1)).setNotesList(list);
//            }
//        });


//        Flowable.just("")
//                .map(s -> {
//                    RealmConfiguration config =
//                            new RealmConfiguration.Builder().inMemory().name("test-realm").build();
//                    return Realm.getInstance(config);
//                })
//                .map(realm1 -> {
//                    RealmResults<Note> list = realm1.where(Note.class).findAll();
//                    when(dataManager.loadNotes()).thenReturn(Observable.just(list));
//                    return list;
//                })
//                .map(notes1 -> {
//                    presenter.loadNotesRx();
//                    verify(viewState, times(1)).setNotesList(notes1);
//                    return "";
//                })
//                .subscribeOn(AndroidSchedulers.mainThread())
//                .subscribe();
//    }

    }
}
