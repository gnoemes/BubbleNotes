package com.gnoemes.bubblenotes.repo.local;

import com.gnoemes.bubblenotes.repo.local.LocalRepository;
import com.gnoemes.bubblenotes.repo.model.Comment;
import com.gnoemes.bubblenotes.repo.model.Description;
import com.gnoemes.bubblenotes.repo.model.Note;
import com.gnoemes.bubblenotes.repo.model.Note_;
import com.gnoemes.bubblenotes.util.CommonUtils;

import java.util.List;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.Property;
import io.objectbox.query.Query;
import io.objectbox.rx.RxBoxStore;
import io.objectbox.rx.RxQuery;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;
import timber.log.Timber;

/**
 * Created by kenji1947 on 28.09.2017.
 */

public class LocalRepositoryImpl implements LocalRepository {
    private BoxStore boxStore;
    private Box<Note> noteBox;
    private Box<Comment> commentBox;
    private Box<Description> descriptionBox;

    PublishSubject<Boolean> obs;

    public LocalRepositoryImpl(BoxStore boxStore) {
        this.boxStore = boxStore;
        noteBox = boxStore.boxFor(Note.class);
        commentBox = boxStore.boxFor(Comment.class);
        descriptionBox = boxStore.boxFor(Description.class);
        obs = PublishSubject.create();
    }

    public void testMethod() {
        //TODO Подумать над одиночными данными
//        RxQuery.single();
//        RxQuery.flowableOneByOne();
//        RxQuery.observable();

        RxBoxStore.<Note>observable(boxStore).subscribe(aClass -> {

        });

//        Observable.merge(getAllComments(1), getAllNotesOrderBy(null))
//                .subscribe(new Observer<List<? extends Object>>() {});
    }
    @Override
    public Observable<Boolean> subscribeToChangeListenerManager() {
        return obs;
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

    //TODO Возращает из io
    @Override
    public Observable<List<Note>> getAllNotesOrderBy(Property property) {
        Timber.d("getAllNotesOrderBy");
        Query<Note> query2 = noteBox.query()
                .orderDesc(property)
                .eager(Note_.description, Note_.comments)
                .build();
        return RxQuery.observable(query2);
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
        obs.onNext(false);
        return Observable.fromCallable(() -> {
            //CommonUtils.longOperation();
            return noteBox.put(note);
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
    @Override
    public Observable<Boolean> deleteNote(long id) {
        Timber.d("deleteNote");
        obs.onNext(false);
        return Observable.fromCallable(() -> {
            //CommonUtils.longOperation();
            noteBox.remove(id);
            return true;
        });
    }
}

