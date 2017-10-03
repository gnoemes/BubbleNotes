package com.gnoemes.bubblenotes.repo.local;

import com.gnoemes.bubblenotes.repo.model.Comment;
import com.gnoemes.bubblenotes.repo.model.Comment_;
import com.gnoemes.bubblenotes.repo.model.Description;
import com.gnoemes.bubblenotes.repo.model.Note;
import com.gnoemes.bubblenotes.repo.model.Note_;

import java.util.List;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.Property;
import io.objectbox.query.Query;
import io.objectbox.query.QueryBuilder;
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

        //clearAllEntities();

    }

    private void clearAllEntities() {
        noteBox.removeAll();
        commentBox.removeAll();;
        descriptionBox.removeAll();
    }


    @Override
    public List<Note> getAllNotesOrderByData(Property property) {
        Timber.d("getAllNotesOrderBy");
        Query<Note> query = noteBox.query()
                .orderDesc(property)
                .eager(Note_.description, Note_.comments)
                .build();
        return query.find();
    }

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

    //TODO Создаст объект со всеми зависимыми сущностями
    //Будут вызваны обезреверы всех зависимых сущностей
    @Override
    public Observable<Long> addNote(Note note) {
        Timber.d("addNote");
        subjectUpdateListener.onNext(false);
        return Observable.fromCallable(() -> {
            //CommonUtils.longOperation();
            return noteBox.put(note);
        });
    }
    //TODO Delete cascade NOT supported
    //Будут вызваны обезреверы всех зависимых сущностей
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

    //TODO ObjectBox не может обновить всю зависимости через noteBox.put(note);
    //TODO Баг: Создать запись - Добавить,Удалить коммент - Записать

    //Запишет каскадно зависимые сущности только при изменениях типа: добавление/удаление
    //Записывает зависимые сущности при добавлении/удалении, но не записывает их при их изменении?
    //Чтобы записать изменения нужно явно вызывать put()
    //Будут вызваны обезреверы всех зависимых сущностей
    @Override
    public Observable<Long> UpdateNote(Note note) {
        Timber.d("UpdateNote");
        subjectUpdateListener.onNext(false);
        return Observable.fromCallable(() -> {
            //CommonUtils.longOperation();

            boxStore.runInTx(() -> {
                //update desc
                descriptionBox.put(note.getDescription().getTarget());

                //Add new comments
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

    //TODO Not used
    //------------------------------------------------------------------------------
    @Override
    public Observable<List<Comment>> getCommentsById(long note_id) {
        Query<Comment> commentQuery = commentBox.query().equal(Comment_.noteToOneId, note_id).build();
        return RxQuery.observable(commentQuery);
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

}

