package com.gnoemes.bubblenotes;

import android.support.test.annotation.UiThreadTest;
import android.support.test.rule.UiThreadTestRule;

import com.gnoemes.bubblenotes.data.model.Note;
import com.gnoemes.bubblenotes.data.source.DataManager;
import com.gnoemes.bubblenotes.data.source.DataManagerDefault;
import com.gnoemes.bubblenotes.data.source.local.RealmManager;
import com.gnoemes.bubblenotes.ui.note_detail.NoteDetailPresenter;
import com.gnoemes.bubblenotes.ui.note_detail.NoteDetailView$$State;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by kenji1947 on 27.09.2017.
 */

public class NoteDetailPresenterTest {
    @Mock
    NoteDetailView$$State viewState;

    DataManager dataManager;
    NoteDetailPresenter presenter;
    Realm realm;
    @Rule
    public UiThreadTestRule uiThreadTestRule = new UiThreadTestRule();

    static String id = "1";
    static String name = "asd";
    static int p = 1;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        RealmConfiguration config =
                new RealmConfiguration.Builder().inMemory().name("test-realm").build();
        realm = Realm.getInstance(config);

        //TODO Inject Schedulers in Presenter. Schedulers.immediate()
        presenter = new NoteDetailPresenter(realm,
                Schedulers.trampoline(),
                Schedulers.trampoline(),
                new DataManagerDefault(null, null),
                "1");
        presenter.setViewState(viewState);
    }

    @After
    public void tearDown() throws Exception {
        realm.close();
    }


    @UiThreadTest
    @Test
    public void addNoteTest_ShouldSetNote() {
        presenter.addNote(id, name, p);
        verify(viewState, times(1)).showToast("Note saved " + id);

        //TODO Проверить
        Note note = realm.where(Note.class).equalTo("id", id).findFirst();
    }

    @UiThreadTest
    @Test
    public void getNote_ShouldSetNote() {
        realm.executeTransaction(realm1 -> {
            Note note = realm1.createObject(Note.class, id);
            note.setName(name);
            note.setPriority(p);
        });

        presenter.getNote(id);
        Note note = realm.where(Note.class).equalTo("id", id).findFirst();
        verify(viewState, times(1)).setNote(note);
    }

    @UiThreadTest
    @Test
    public void updateNote_ShouldSetNote() {
        realm.executeTransaction(realm1 -> {
            Note note = realm1.createObject(Note.class, id);
            note.setName(name);
            note.setPriority(p);
        });

        String n = "sdsdf";
        int p = 34;

        presenter.updateNote(id, n, p);

        verify(viewState, times(1)).showToast("Note updated");
        verify(viewState, times(1)).backPressed();

        //TODO Проеерить поля
        Note note = realm.where(Note.class).equalTo("id", id).findFirst();

    }

    @UiThreadTest
    @Test
    public void deleteNote_ShouldSetNote() {
        realm.executeTransaction(realm1 -> {
            Note note = realm1.createObject(Note.class, id);
            note.setName(name);
            note.setPriority(p);
        });

        presenter.deleteNote(id);

        verify(viewState, times(1)).showToast("Note deleted");
        verify(viewState, times(1)).backPressed();

        //TODO Проеерить что удалено
        Note note = realm.where(Note.class).equalTo("id", id).findFirst();
    }
}
