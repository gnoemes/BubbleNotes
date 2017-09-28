package com.gnoemes.bubblenotes.ui.notes_list;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.gnoemes.bubblenotes.App;
import com.gnoemes.bubblenotes.data.model.Note;
import com.gnoemes.bubblenotes.data.source.DataManager;
import com.gnoemes.bubblenotes.repo.local.LocalRepository;

import javax.inject.Inject;

import io.reactivex.Scheduler;
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

    DataManager dataManager;
    LocalRepository localRepository;
    Realm realm;
    Scheduler main;
    Scheduler io;

    public NotesListPresenter(Realm realm, Scheduler main, Scheduler io,
                              DataManager dataManager,
                              LocalRepository localRepository) {
        this.dataManager = dataManager;
        this.localRepository = localRepository;
        this.realm = realm;
        this.main = main;
        this.io = io;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        App.getAppComponent().inject(this);
        loadNotesRx();
    }

    //Async Rx version
    public void loadNotesRx() {
        localRepository.loadNotesSorted(realm, "priority")
                .subscribe();

        //TODO Остановить disposable в onDestroy
        Disposable disposable = localRepository.loadNotesSorted(realm, "priority").subscribe(notes -> {
            notes.asChangesetObservable()
                    //.filter(realmResultsCollectionChange -> realmResultsCollectionChange.getCollection().isValid())
                    .subscribeOn(main)
                    .subscribe(realmResultsCollectionChange -> {
                        Timber.d("accept");
                        if (realmResultsCollectionChange.getCollection().isLoaded()) {
                            if (realmResultsCollectionChange.getChangeset() == null) {
                                Timber.d("realmResultsCollectionChange.getChangeset() == null");
                                getViewState().setNotesList(realmResultsCollectionChange.getCollection());
                            }
                        }
                        getViewState().setChangeSet(realmResultsCollectionChange.getChangeset());

                    }, throwable -> {
                        Timber.d("Error " + throwable);
                    });
        });

    }

    //Stop all work, because View was stopped. This method NOT be triggered by configuration change.
    public void onStop() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}
