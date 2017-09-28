package com.gnoemes.bubblenotes;

import android.support.test.annotation.UiThreadTest;
import android.support.test.rule.UiThreadTestRule;
import android.util.Log;

import com.gnoemes.bubblenotes.data.model.Note;
import com.gnoemes.bubblenotes.data.source.DataManagerDefault;
import com.gnoemes.bubblenotes.repo.local.LocalRepository;
import com.gnoemes.bubblenotes.repo.local.LocalRepositoryImpl;
import com.gnoemes.bubblenotes.ui.note_detail.NoteDetailPresenter;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmConfiguration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by kenji1947 on 28.09.2017.
 */

public class LocalRepositoryTest {
    LocalRepository localRepository;
    Realm realm;
    @Rule
    public UiThreadTestRule uiThreadTestRule = new UiThreadTestRule();

    static String id = "5";
    static String name = "asd";
    static int priority = 3;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        realm = Injector.getInMemoryRealm();
        localRepository = new LocalRepositoryImpl();
    }

    @After
    public void tearDown() throws Exception {
        realm.close();
    }

    @UiThreadTest
    @Test
    public void addNote() {
        Note note = realm.where(Note.class).equalTo("id", id).findFirst();
        assertEquals(null, note);

        localRepository.addNote(id, name, priority)
                .subscribeOn(Schedulers.trampoline())
                .observeOn(Schedulers.trampoline())
                .subscribe(s -> {
                            Log.i("TEST", "onNext " + s);
                        },
                        throwable -> {
                            Log.i("onError", "onError" + throwable.getMessage());
                            throwable.printStackTrace();
                        },
                        () -> {
                            Log.i("TEST", "onComplete");
                            Note n = realm.where(Note.class).equalTo("id", id).findFirst();
                            assertNotEquals(null, n);
                            assertEquals(name, n.getName());
                        });
    }


    @UiThreadTest
    @Test
    public void deleteNoteTest() {
        realm.executeTransaction(realm1 -> {
            Note note = realm1.createObject(Note.class, id);
            note.setName(name);
            note.setPriority(priority);
        });

        Note note = realm.where(Note.class).equalTo("id", id).findFirst();
        assertNotEquals(null, note);

        localRepository.deleteNote(id)
                .subscribeOn(Schedulers.trampoline())
                .observeOn(Schedulers.trampoline())
                .subscribe(s -> {
                            Log.i("TEST", "onNext " + s);
                        },
                        Throwable::printStackTrace,
                        () -> {
                            Log.i("TEST", "onComplete");
                            Note n = realm.where(Note.class).equalTo("id", id).findFirst();
                            assertEquals(null, n);
                        });
    }
}
