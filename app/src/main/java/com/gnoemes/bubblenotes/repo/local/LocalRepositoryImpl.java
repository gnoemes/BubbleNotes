package com.gnoemes.bubblenotes.repo.local;

import com.gnoemes.bubblenotes.repo.model.Comment;
import com.gnoemes.bubblenotes.repo.model.Comment_;
import com.gnoemes.bubblenotes.repo.model.Description;
import com.gnoemes.bubblenotes.repo.model.Note;
import com.gnoemes.bubblenotes.repo.model.Note_;
import com.gnoemes.bubblenotes.util.CommonUtils;

import java.util.ArrayList;
import java.util.List;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.Property;
import io.objectbox.query.Query;
import io.objectbox.query.QueryBuilder;
import io.objectbox.rx.RxBoxStore;
import io.objectbox.rx.RxQuery;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;
import timber.log.Timber;

/**
 * Created by kenji1947 on 28.09.2017.
 */

public class LocalRepositoryImpl implements LocalRepository {
    private BoxStore boxStore;

    private Box<Note> noteBox;
    private Box<Comment> commentBox;
    private Box<Description> descriptionBox;

    private PublishSubject<Boolean> subjectUpdateListener;

    public LocalRepositoryImpl(BoxStore boxStore) {
        this.boxStore = boxStore;

        noteBox = boxStore.boxFor(Note.class);
        commentBox = boxStore.boxFor(Comment.class);
        descriptionBox = boxStore.boxFor(Description.class);

        subjectUpdateListener = PublishSubject.create();

        //clearAllEntities();
    }

    private void clearAllEntities() {
        noteBox.removeAll();
        commentBox.removeAll();;
        descriptionBox.removeAll();
    }

    @Override
    public Observable<Boolean> subscribeToChangeListenerManager() {
        return subjectUpdateListener;
    }

    @Override
    public List<Note> getAllNotesOrderBy(Property property) {
        Timber.d("getAllNotesOrderByObservable");
        Query<Note> query = noteBox.query()
                .orderDesc(property)
                .eager(Note_.description, Note_.comments)
                .build();
        return query.find();
    }


    @Override
    public Observable<List<Comment>> getAllComments() {
        Query<Comment> commentQuery = commentBox.query().build();
        return RxQuery.observable(commentQuery);
    }

    @Override
    public Observable<List<Description>> getAllDescription() {
        Query<Description> descriptionQuery = descriptionBox.query().build();
        return RxQuery.observable(descriptionQuery);
    }

    @Override
    public Observable<List<Note>> getAllNotesOrderByObservable(Property property) {
        Timber.d("getAllNotesOrderByObservable");
        //CommonUtils.longOperation();
        Query<Note> query = noteBox.query()
                .orderDesc(property)
                .eager(Note_.description, Note_.comments)
                .build();
        return RxQuery.observable(query);
    }


    @Override
    public Observable<Note> getNote(long id) {
        Timber.d("getNote");
        return Observable.<Note>fromCallable(() -> {
            //CommonUtils.longOperation();
            return noteBox.get(id);});
    }

    @Override
    public Observable<Long> addNote(Note note) {
        Timber.d("addNote");
        subjectUpdateListener.onNext(false);
        return Observable.fromCallable(() -> {
            //CommonUtils.longOperation();
            return noteBox.put(note);
        });
    }
    @Override
    public Observable<Boolean> deleteNote(long id) {
        Timber.d("deleteNote");
        subjectUpdateListener.onNext(false);
        return Observable.fromCallable(() -> {
            //CommonUtils.longOperation();
            boxStore.runInTx(() -> {
                Note note = noteBox.get(id);

                descriptionBox.remove(note.getDescription().getTargetId());
                commentBox.remove(note.getComments());

                noteBox.remove(id);
            });
            return true;
        });
    }

    @Override
    public Observable<Long> UpdateNote(Note note) {
        Timber.d("UpdateNote");
        subjectUpdateListener.onNext(false);
        return Observable.fromCallable(() -> {
            //CommonUtils.longOperation();
            boxStore.runInTx(() -> {
                descriptionBox.put(note.getDescription().getTarget());

                noteBox.put(note);

                //TODO Уродское удаление unmanaged relation
                List<Comment> list = commentBox.getAll();
                for (Comment c: list) {
                    if (c.getNoteToOne().getTarget() == null) {
                        commentBox.remove(c);
                    }
                }
            });

            return note.getId();
        });

    }
}

