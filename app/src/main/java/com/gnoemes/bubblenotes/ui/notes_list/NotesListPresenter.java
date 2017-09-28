package com.gnoemes.bubblenotes.ui.notes_list;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.gnoemes.bubblenotes.data.source.NoteRepository;
import com.gnoemes.bubblenotes.utils.RxUtil;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

/**
 * Created by kenji1947 on 25.09.2017.
 */

@InjectViewState
public class NotesListPresenter extends MvpPresenter<NotesListView> {

    private NoteRepository repository;
    private CompositeDisposable subscriptions = new CompositeDisposable();

    @Inject
    public NotesListPresenter(NoteRepository repository) {
        this.repository = repository;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        loadNotesRx();
    }


    //Async Rx version
    private void loadNotesRx() {
        subscriptions.add(repository.loadNotes()
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
                }));
    }


    //Stop all work, because View was stopped. This method NOT be triggered by configuration change.
    public void onStop() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxUtil.unsubscribe(subscriptions);
    }
}
