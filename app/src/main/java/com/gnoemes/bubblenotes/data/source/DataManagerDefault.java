package com.gnoemes.bubblenotes.data.source;


import com.gnoemes.bubblenotes.data.model.Note;
import com.gnoemes.bubblenotes.data.source.local.RealmManager;
import com.gnoemes.bubblenotes.data.source.remote.RemoteManager;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.exceptions.RealmError;

/**
 * DataManagerDefault which controls Realm Database, API
 */

@Singleton
public class DataManagerDefault implements DataManager {

    private final RealmManager realmManager;
    private final RemoteManager remoteDataManager;

    @Inject
    public DataManagerDefault(RealmManager realmManager, RemoteManager remoteDataManager) {
        this.realmManager = realmManager;
        this.remoteDataManager = remoteDataManager;
    }


    //TODO Методы можно сделать статическими
    //TODO Сделать async
    @Override
    public Flowable<RealmResults<Note>> loadNotesSorted(Realm realm, String field) {
        //return realm.where(Note.class).findAllSortedAsync(field).asFlowable();
        return Flowable.just(realm.where(Note.class).findAllSorted(field));
    }


    @Override
    public Observable<Note> loadNote(Realm realm, String id) {
        return Observable.just(realm.where(Note.class).equalTo("id", id).findFirst());
        //return Observable.error(new RealmError(""));
    }

    @Override
    public Observable<String> addNoteNew(Realm realm, String id, String name, int priority) {
        realm.executeTransaction(realm1 -> {
            Note note = new Note();
            note.setId(id);
            note.setName(name);
            note.setPriority(priority);
            realm1.copyToRealmOrUpdate(note);
        });
        return Observable.just(id);
        //return Observable.error(new RealmError(""));
    }


    public Observable<String> addNoteNew2(String id, String name, int priority) {
        return Observable.create(e -> {
            Realm realm = null;
            try {
                realm = Realm.getDefaultInstance();
                realm.executeTransactionAsync(realm12 -> {
                            Note note = new Note();
                            note.setId(id);
                            note.setName(name);
                            note.setPriority(priority);
                            realm12.copyToRealmOrUpdate(note);
                            e.onNext(id);
                        },
                        () -> {
                            e.onComplete();
                        },
                        error -> {
                            e.onError(error);
                        });
            } finally {
                if (realm != null) {
                    realm.close();
                }
            }
        });
    }

    public Observable<String> addNoteNew3(String id, String name, int priority) {
        return Observable.create(e -> {
            Realm realm = null;
            try {
                realm = Realm.getDefaultInstance();
                realm.executeTransaction(realm12 -> {
                    Note note = new Note();
                    note.setId(id);
                    note.setName(name);
                    note.setPriority(priority);
                    realm12.copyToRealmOrUpdate(note);
                    e.onNext(id);
                    e.onComplete();
                });
            }
            catch (Throwable t) {
                e.onError(t);
            }
            finally {
                if (realm != null) {
                    realm.close();
                }
            }
        });
    }


    public Observable<String> deleteNote(Realm realm, String id) {
        realm.executeTransaction(realm1 -> {
            Note note = realm1.where(Note.class).equalTo("id", id).findFirst();
            note.deleteFromRealm();
        });
        return Observable.just(id);
    }

    public Observable<String> updateNote(Realm realm, String id, String name, int priority) {
        realm.executeTransaction(realm1 -> {
            Note note = realm1.where(Note.class).equalTo("id", id).findFirst();
            if(note == null)
                return;
            note.setName(name);
            note.setPriority(priority);
        });
        return Observable.just(id);
    }


    @Override
    public Observable<Note> loadNotes(String id) {
        return realmManager.findNoteById(id);
    }

    @Override
    public Observable<RealmResults<Note>> loadNotes() {
        return realmManager.findAllNotes();
    }

    @Override
    public Observable<Boolean> addNote(String id, String name, int priority) {
        return realmManager.addNote(id,name,priority);
    }

    @Override
    public Observable<String> addNote(String name, int priority) {
        return null;
    }


    @Override
    public Observable<Boolean> updateNote(String id, String name, int priority) {
        return realmManager.updateNote(id,name,priority);
    }

    @Override
    public Observable<Boolean> deleteNote(String id) {
        return realmManager.deleteNote(id);
    }
}
