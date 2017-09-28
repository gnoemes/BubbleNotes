package com.gnoemes.bubblenotes.notes;

import android.support.test.InstrumentationRegistry;
import android.support.test.annotation.UiThreadTest;
import android.support.test.runner.AndroidJUnit4;

import com.gnoemes.bubblenotes.data.model.Note;
import com.gnoemes.bubblenotes.data.source.local.NoteLocalDataSource;
import com.gnoemes.bubblenotes.utils.NoteMapper;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.reactivex.observers.TestObserver;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import io.realm.internal.IOException;
import io.realm.rx.CollectionChange;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class NoteLocalDataSourceTest {
    private NoteLocalDataSource source;

    private Realm realm;

    @Before
    @UiThreadTest
    public void setup() throws IOException {
        Realm.init(InstrumentationRegistry.getTargetContext());
        RealmConfiguration config = new RealmConfiguration.Builder().inMemory().build();
        realm = Realm.getInstance(config);

        source = new NoteLocalDataSource(realm);
    }

    @After
    @UiThreadTest
    public void tearDown() {
        realm.close();
        Realm.deleteRealm(realm.getConfiguration());
    }

    @Test
    @UiThreadTest
    public void loadNotesShouldReturnEmptyNotes() {
        TestObserver<CollectionChange<RealmResults<Note>>> testObserver = source.loadNotes().test();
        testObserver.assertNoErrors();
        testObserver.assertNoValues();
        testObserver.assertComplete();
    }

    @Test
    @UiThreadTest
    public void addedOrUpdateShouldStorage2() {
        Note note1 = NoteMapper.createNoteFromData("1","name",1);
        Note note2 = NoteMapper.createNoteFromData("2","name",2);
        TestObserver<Note> testObserver = source.addOrUpdateNote(note1).test();
        testObserver.assertNoErrors();

        testObserver = source.addOrUpdateNote(note2).test();
        testObserver.assertNoErrors();

        assertEquals(2, getNotes().size());
    }

    @Test
    @UiThreadTest
    public void addedOrUpdateShouldOverrideNote1() {
        Note note1 = NoteMapper.createNoteFromData("1","name",1);
        Note note2 = NoteMapper.createNoteFromData("1","name",2);

        saveInDb(note1);

        TestObserver<Note> testObserver = source.addOrUpdateNote(note2).test();
        testObserver.assertNoErrors();

        assertEquals(1, getNotes().size());
        assertEquals(note1.getId(), getNotes().get(0).getId());
        assertEquals(note1.getName(),getNotes().get(0).getName());
        assertEquals(note2.getPriority(),getNotes().get(0).getPriority());
    }


    private RealmResults<Note> getNotes() {
        return realm.where(Note.class).findAll();
    }

    private void saveInDb(Note note) {
        realm.executeTransaction(realm -> realm.copyToRealm(note));
    }
}
