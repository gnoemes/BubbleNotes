package com.gnoemes.bubblenotes.data.source.local;

import com.gnoemes.bubblenotes.App;
import com.gnoemes.bubblenotes.data.model.Note;

import java.util.UUID;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.realm.RealmResults;

public class RealmManagerDefault implements RealmManager {

    @Inject
    RealmDatabase realmDatabase;

    public RealmManagerDefault() {
        App.getAppComponent().inject(this);
    }


    @Override
    public Observable<Note> findNoteById(final String id) {
        return Observable.create(subscriber -> {
            try {
                Note note = realmDatabase.findById(Note.class,id);
                subscriber.onNext(note);
                subscriber.onComplete();
            }
            catch (Exception e) {
                subscriber.onError(e);
            }
        });
    }

    @Override
    public Observable<RealmResults<Note>> findAllNotes() {
        return Observable.create(subscriber -> {
            try {
                RealmResults<Note> notes = realmDatabase.findAll(Note.class);
                subscriber.onNext(notes);
                subscriber.onComplete();
            } catch (Exception e) {
                subscriber.onError(e);
            }
        });
    }

    @Override
    public Observable<Boolean> addNote(String id, String name, int priority) {
        return Observable.create(subscriber -> {
            try {
                Note note = new Note();
                note.setId(id);
                note.setName(name);
                note.setPriority(priority);
                boolean added = realmDatabase.add(note) != null;
                subscriber.onNext(added);
                subscriber.onComplete();
            } catch (Exception e) {
                subscriber.onError(e);
            }
        });
    }

    @Override
    public Observable<String> addNote(String name, int priority) {
        return Observable.create(subscriber -> {
            try {
                String id = UUID.randomUUID().toString();
                Note note = new Note();
                note.setId(id);
                note.setName(name);
                note.setPriority(priority);
                boolean added = realmDatabase.add(note) != null;
                subscriber.onNext(id);
                subscriber.onComplete();
            } catch (Exception e) {
                subscriber.onError(e);
            }
        });
    }

    @Override
    public Observable<Boolean> updateNote(String id, String name, int priority) {
        return Observable.create(subscriber -> {
            try {
                Note note = new Note();
                note.setId(id);
                note.setName(name);
                note.setPriority(priority);
                boolean updated = realmDatabase.add(note) != null;
                subscriber.onNext(updated);
                subscriber.onComplete();
            } catch (Exception e) {
                subscriber.onError(e);
            }
        });
    }

    @Override
    public Observable<Boolean> deleteNote(String id) {
        return Observable.create(subscriber -> {
            try {
                realmDatabase.delete(Note.class,id);
                Note note = realmDatabase.findById(Note.class,id);
                boolean deleted = note == null;
                subscriber.onNext(deleted);
                subscriber.onComplete();
            } catch (Exception e) {
                subscriber.onError(e);
            }
        });
    }


}
