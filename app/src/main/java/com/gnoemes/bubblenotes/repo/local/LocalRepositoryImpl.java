package com.gnoemes.bubblenotes.repo.local;

import android.support.annotation.UiThread;
import android.support.annotation.WorkerThread;

import com.gnoemes.bubblenotes.data.model.Note;
import com.gnoemes.bubblenotes.util.CommonUtils;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.rx.CollectionChange;
import timber.log.Timber;

/**
 * Created by kenji1947 on 27.09.2017.
 */

public class LocalRepositoryImpl implements LocalRepository {
    //TODO Методы можно сделать статическими

    private <T> Observable<T> wrap() {
        return null;
    }

    @UiThread
    @Override
    public Flowable<RealmResults<Note>> loadNotesSorted(Realm realm, String field) {
        Timber.d("loadNotesSorted");
        return Flowable.fromCallable(() -> {
            // CommonUtils.longOperation();
            return realm.where(Note.class).findAllSorted(field);
        });
    }

    @Override
    public Observable<String> addNote(String id, String name, int priority) {
        Timber.d("addNote");
        return Observable.fromCallable(() -> {
            Realm realm = null;
            try {
                realm = Realm.getDefaultInstance();
                realm.executeTransaction(realm1 -> {
                    Note note = new Note();
                    note.setId(id);
                    note.setName(name);
                    note.setPriority(priority);
                    realm1.copyToRealmOrUpdate(note);
                });
            } finally {
                if (realm != null) {
                    realm.close();
                }
            }
            // CommonUtils.longOperation();
            return id;
        });
    }

    @Override
    public Observable<String> updateNote(String id, String name, int priority) {
        Timber.d("updateNote");

        return Observable.fromCallable(() -> {
            Realm realm = null;
            try {
                realm = Realm.getDefaultInstance();
                realm.executeTransaction(realm1 -> {
                    Note note = realm1.where(Note.class).equalTo("id", id).findFirst();
                    if(note == null)
                        return; //TODO Observable.error("Not found");
                    note.setName(name);
                    note.setPriority(priority);
                });
            } finally {
                if (realm != null) {
                    realm.close();
                }
            }
            //CommonUtils.longOperation();
            return id;
        });
    }

    @UiThread
    @Override
    public Observable<Note> loadNote(Realm realm, String id) {
        Timber.d("loadNote");
        return Observable.fromCallable(() -> {
            //longOperation();
            return realm.where(Note.class).equalTo("id", id).findFirst();
        });
    }

    @WorkerThread
    @Override
    public Observable<String> deleteNote(String id) {
        Timber.d("deleteNote");

        return Observable.fromCallable(() -> {
            Realm realm = null;
            try {
                realm = Realm.getDefaultInstance();
                realm.executeTransaction(realm1 -> {
                    Note note = realm1.where(Note.class).equalTo("id", id).findFirst();
                    Timber.d("note != null " + (note == null));
                    if (note != null)
                        note.deleteFromRealm();
                });
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            } finally {
                if (realm != null) {
                    realm.close();
                }
            }
            //CommonUtils.longOperation();
            return id;
        });
    }
}
