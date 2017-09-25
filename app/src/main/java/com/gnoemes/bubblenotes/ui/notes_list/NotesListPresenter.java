package com.gnoemes.bubblenotes.ui.notes_list;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.gnoemes.bubblenotes.App;
import com.gnoemes.bubblenotes.data.source.Manager;
import com.gnoemes.bubblenotes.data.model.Note;

import java.util.UUID;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.rx.CollectionChange;
import timber.log.Timber;

/**
 * Created by kenji1947 on 25.09.2017.
 */

@InjectViewState
public class NotesListPresenter extends MvpPresenter<NotesListView> {
    private Realm realm;

    @Inject
    Manager.DataManager dataManager;

    public NotesListPresenter(Realm realm) {
        this.realm = realm;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        App.getAppComponent().inject(this);
        loadNotes();
    }

    //TODO Draft sync version. Change to Rx.
    private void loadNotes() {
        RealmResults<Note> notes = dataManager.loadNotes(Note.class);
//        RealmResults<Note> notes = realm.where(Note.class).findAllSorted("priority");
        getViewState().setNotesList(notes);
    }

    //Async Rx version
    private void loadNotesRx() {
        //TODO Остановить disposable в onStop
        Disposable disposable = realm.where(Note.class).findAllSortedAsync("priority")
                .<RealmResults<Note>>asChangesetObservable()
                //.filter(realmResultsCollectionChange -> realmResultsCollectionChange.getCollection().isValid())
                //.filter(realmResultsCollectionChange -> realmResultsCollectionChange.getCollection().isLoaded())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CollectionChange<RealmResults<Note>>>() {
                    @Override
                    public void accept(CollectionChange<RealmResults<Note>> realmResultsCollectionChange) throws Exception {
                        Timber.d("accept");
                        //TODO Проверить логику уведомлений
                        //Нужно отдать RealmResults<Note> в первый раз.
                        //Дальше передавать changeset
                        if (realmResultsCollectionChange.getCollection().isLoaded()) {

                           /* if (realmResultsCollectionChange.getChangeset() == null) {
                                Timber.d("realmResultsCollectionChange.getChangeset() == null");
                                getViewState().setNotesList(realmResultsCollectionChange.getCollection());
                            } else {
                                Timber.d("realmResultsCollectionChange.getChangeset() != null");
                                getViewState().setChangeSet(realmResultsCollectionChange.getChangeset());
                            }*/

                            if (realmResultsCollectionChange.getChangeset() == null) {
                                Timber.d("realmResultsCollectionChange.getChangeset() == null");
                                getViewState().setNotesList(realmResultsCollectionChange.getCollection());
                            }
                            getViewState().setChangeSet(realmResultsCollectionChange.getChangeset());

                        }
                    }
                }, throwable -> {

                });
    }

    //Stop all work, because View was stopped. This method NOT be triggered by configuration change.
    public void onStop() {

    }

    public void addNote(String name, int priority) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Note note = realm.createObject(Note.class, UUID.randomUUID().toString());
                note.setName(name);
                note.setPriority(priority);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        realm.removeAllChangeListeners();
        realm.close();
    }
}
