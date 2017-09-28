package com.gnoemes.bubblenotes.repo.local;

import com.gnoemes.bubblenotes.data.model.Note;
import com.gnoemes.bubblenotes.util.CommonUtils;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by kenji1947 on 28.09.2017.
 */

public class LocalRepositoryWrong implements LocalRepository {
    @Override
    public Flowable<RealmResults<Note>> loadNotesSorted(Realm realm, String field) {
        //return realm.where(Note.class).findAllSortedAsync(field).asFlowable();
        CommonUtils.longOperation();
        return Flowable.just(realm.where(Note.class).findAllSorted(field));
    }

    @Override
    public Observable<String> addNote(String id, String name, int priority) {
        //TODO Realm полученный с мейна будет работать в фоне
        //        return Observable.fromCallable(() -> {
//            realm.executeTransaction(realm1 -> {
//                Note note = new Note();
//                note.setId(id);
//                note.setName(name);
//                note.setPriority(priority);
//                realm1.copyToRealmOrUpdate(note);
//            });
//            longOperation();
//            return id;
//        });

        //TODO WILL EXECUTE ON MAIN THREAD!
//        realm.executeTransaction(realm1 -> {
//            Note note = new Note();
//            note.setId(id);
//            note.setName(name);
//            note.setPriority(priority);
//            realm1.copyToRealmOrUpdate(note);
//        });
//        longOperation();
//        return Observable.just(id);

        //return Observable.error(new RealmError(""));
        return null;
    }

    @Override
    public Observable<Note> loadNote(Realm realm, String id) {
        //return Observable.just(realm.where(Note.class).equalTo("id", id).findFirst());
        //return Observable.error(new RealmError(""));
        return null;
    }

    @Override
    public Observable<String> deleteNote(String id) {


//        return Observable.fromCallable(() -> {
//            realm.executeTransaction(realm1 -> {
//                Note note = realm1.where(Note.class).equalTo("id", id).findFirst();
//                if (note != null)
//                    note.deleteFromRealm();
//            });
//            longOperation();
//            return id;
//        });

//        realm.executeTransaction(realm1 -> {
//            Note note = realm1.where(Note.class).equalTo("id", id).findFirst();
//            if (note != null)
//                note.deleteFromRealm();
//        });
//         CommonUtils.longOperation();
//        return Observable.just(id);
        return null;
    }

    @Override
    public Observable<String> updateNote(String id, String name, int priority) {
        return null;
    }
}
