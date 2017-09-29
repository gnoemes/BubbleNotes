package com.gnoemes.bubblenotes.ui.notes_list;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.gnoemes.bubblenotes.data.source.NoteRepository;
import com.gnoemes.bubblenotes.utils.RxUtil;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

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
                    .compose(RxUtil.applyFlowableSchedulers())
                    .subscribe(notes -> getViewState().setNotesList(notes)));
    }

    public void deleteNote(String id) {
        subscriptions.add(repository.deleteNote(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    getViewState().showToast("Note deleted");
                }, Throwable::printStackTrace));
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
