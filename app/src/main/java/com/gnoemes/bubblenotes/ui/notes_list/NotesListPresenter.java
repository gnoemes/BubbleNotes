package com.gnoemes.bubblenotes.ui.notes_list;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.gnoemes.bubblenotes.App;
import com.gnoemes.bubblenotes.data.model.Note;
import com.gnoemes.bubblenotes.data.source.DataManager;

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
    DataManager dataManager;

    public NotesListPresenter(Realm realm) {
        this.realm = realm;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        App.getAppComponent().inject(this);
        //loadNotes();
        loadNotesRx();
    }

    //Draft sync version. Change to Rx.
    private void loadNotes() {
//        RealmResults<Note> notes = dataManager.loadNotes(Note.class);
//        RealmResults<Note> notes = realm.where(Note.class).findAllSorted("priority");
//        getViewState().setNotesList(notes);
    }

    //Async Rx version
    //
    private void loadNotesRx() {
        //TODO Остановить disposable в onDestroy
        Disposable disposable = dataManager.loadNotes().subscribe(notes ->
                notes.asChangesetObservable()
                //.filter(realmResultsCollectionChange -> realmResultsCollectionChange.getCollection().isValid())
                //.filter(realmResultsCollectionChange -> realmResultsCollectionChange.getCollection().isLoaded())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CollectionChange<RealmResults<Note>>>() {
                    @Override
                    public void accept(CollectionChange<RealmResults<Note>> realmResultsCollectionChange) throws Exception {
                        Timber.d("accept");

                        if (realmResultsCollectionChange.getCollection().isLoaded()) {
                            if (realmResultsCollectionChange.getChangeset() == null) {
                                Timber.d("realmResultsCollectionChange.getChangeset() == null");
                                getViewState().setNotesList(realmResultsCollectionChange.getCollection());
                            }
                        }
                        getViewState().setChangeSet(realmResultsCollectionChange.getChangeset());

                    }
                }, throwable -> {
                    Timber.d("Error " + throwable);
                }));

    }

    //Stop all work, because View was stopped. This method NOT be triggered by configuration change.
    public void onStop() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        realm.removeAllChangeListeners();
        realm.close();
    }
}
