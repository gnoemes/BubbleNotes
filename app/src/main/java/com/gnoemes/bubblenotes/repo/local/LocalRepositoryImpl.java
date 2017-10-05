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

    private PublishSubject<Boolean> subjectNoteForeignChangesStatus;

    public LocalRepositoryImpl(BoxStore boxStore) {
        this.boxStore = boxStore;

        noteBox = boxStore.boxFor(Note.class);
        commentBox = boxStore.boxFor(Comment.class);
        descriptionBox = boxStore.boxFor(Description.class);

        subjectNoteForeignChangesStatus = PublishSubject.create();

        //clearAllEntities();
    }

    private void clearAllEntities() {
        noteBox.removeAll();
        commentBox.removeAll();;
        descriptionBox.removeAll();
    }

    @Override
    public Observable<Boolean> observeNoteForeignChangesStatus() {
        return subjectNoteForeignChangesStatus;
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
    public Observable<List<Note>> getAllNotesSorted(Property property) {
        Timber.d("getAllNotesSorted");
        //CommonUtils.longOperation();
        Query<Note> query = noteBox.query()
                .orderDesc(property)
                .eager(Note_.description, Note_.comments)
                .build();
        return RxQuery.observable(query);
    }

    @Override
    public List<Note> getAllNotesSortedList(Property property) {
        Timber.d("getAllNotesSorted");
        Query<Note> query = noteBox.query()
                .orderDesc(property)
                .eager(Note_.description, Note_.comments)
                .build();
        return query.find();
    }


    @Override
    public Observable<Note> getNote(long id) {
        Timber.d("getNote");
        return Observable.<Note>fromCallable(() -> {
            //CommonUtils.longOperation();
            return noteBox.get(id);});
    }

    //Создание новой Note
    @Override
    public Observable<Long> addNote(Note note) {
        Timber.d("addNote");
        subjectNoteForeignChangesStatus.onNext(false);
        return Observable.fromCallable(() -> {
            //CommonUtils.longOperation();
            return noteBox.put(note);
        });
    }

    //Каскадное сохранение.


    //Обновление сохраненной Note.
    //Каскадное сохранение дочерних сущностей произойдет при добавлении новой сущности(без id).
    //При удалении дочерней сущности из связи, последняя будет отвязана от родительской но останется в бд.
    //Для сохранения изменения в дочерних нужных сохранять их явно
    @Override
    public Observable<Long> UpdateNote(Note note) {
        Timber.d("UpdateNote");
        subjectNoteForeignChangesStatus.onNext(false);
        return Observable.fromCallable(() -> {
            //CommonUtils.longOperation();
            boxStore.runInTx(() -> {

                //явное сохранение изменений в дочерней сущности
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

    @Override
    public Observable<Boolean> deleteNote(long id) {
        Timber.d("deleteNote");
        subjectNoteForeignChangesStatus.onNext(false);
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


}

