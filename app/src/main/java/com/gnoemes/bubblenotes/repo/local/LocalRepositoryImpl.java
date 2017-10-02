package com.gnoemes.bubblenotes.repo.local;

import com.gnoemes.bubblenotes.repo.model.Comment;
import com.gnoemes.bubblenotes.repo.model.Description;
import com.gnoemes.bubblenotes.repo.model.Note;
import com.gnoemes.bubblenotes.repo.model.Note_;

import java.util.List;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.Property;
import io.objectbox.query.Query;
import io.objectbox.rx.RxBoxStore;
import io.objectbox.rx.RxQuery;
import io.reactivex.Observable;
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

    PublishSubject<Boolean> subjectUpdateListener;
    PublishSubject<List<Comment>> subjectComments;
    PublishSubject<Description> subjectDescription;

    public LocalRepositoryImpl(BoxStore boxStore) {
        this.boxStore = boxStore;
        noteBox = boxStore.boxFor(Note.class);
        commentBox = boxStore.boxFor(Comment.class);
        descriptionBox = boxStore.boxFor(Description.class);
        subjectUpdateListener = PublishSubject.create();
        subjectComments = PublishSubject.create();
        subjectDescription = PublishSubject.create();


    }

    //Change listeners
    //--------------------------------------------------------------------------------
    @Override
    public Observable<Boolean> subscribeToChangeListenerManager() {
        return subjectUpdateListener;
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
    public Observable<List<Comment>> addComments(List<Comment> comments) {
        return Observable.fromCallable(() -> {
            commentBox.put(comments);
            List<Comment> newList= commentBox.getAll();
            subjectComments.onNext(newList);
            return newList;
        });
    }
    @Override
    public Observable<Description> addDescription(Description description) {
        return Observable.fromCallable(() -> {
            long id = descriptionBox.put(description);
            Description d = descriptionBox.get(id);
            subjectDescription.onNext(d);
            return d;
        });
    }

   //---------------------------------------------------------------------------------

    //TODO Возращает из io
    @Override
    public Observable<List<Note>> getAllNotesOrderBy(Property property) {
        Timber.d("getAllNotesOrderBy");
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
            noteBox.remove(id);
            return true;
        });
    }
    //TODO Баг: Создать запись - Добавить,Удалить коммент - Записать
    //TODO Спросить как записывать зависимые сущности
    //TODO Записывает зависимые сущности при добавлении/удалениеи, но не записывает их при их изменении?
    // Приходиться записывать зависимые сущности явно так как
    // ObjectBox не может обновить всю зависимости через noteBox.put(note);
    @Override
    public Observable<Long> UpdateNote(Note note) {
        Timber.d("UpdateNote");
        return Observable.fromCallable(() -> {
            //CommonUtils.longOperation();
            descriptionBox.put(note.getDescription().getTarget());
            //note.getComments().reset();
            return noteBox.put(note);
        });

    }

}

