package com.gnoemes.bubblenotes.ui.notes_list;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.gnoemes.bubblenotes.data.note.local.NoteDataSource;
import com.gnoemes.bubblenotes.utils.NoteMapper;
import com.gnoemes.bubblenotes.utils.RxUtil;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by kenji1947 on 25.09.2017.
 */

@InjectViewState
public class NotesListPresenter extends MvpPresenter<NotesListView> {

    private NoteDataSource repository;
    private CompositeDisposable subscriptions = new CompositeDisposable();

    @Inject
    public NotesListPresenter(NoteDataSource repository) {
        this.repository = repository;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        loadNotesRx();
    }


    //Async Rx version
    public void loadNotesRx() {
      subscriptions.add(repository.loadNotes()
                    .compose(RxUtil.applyFlowableSchedulers())
                    .subscribe(notes -> getViewState().setNotesList(notes)));
    }

    public void deleteNote(long id) {
        subscriptions.add(repository.deleteNote(id)
                .compose(RxUtil.applySchedulers())
                .subscribe(aBoolean -> {
                    if(aBoolean) {
                        getViewState().showToast("Note deleted");
                    } else {
                        getViewState().showToast("Error");
                    }
                },Throwable::printStackTrace));
    }

    public void updateNoteState(long id, String name,String description, int priority,String date,boolean complete) {
        subscriptions.add(repository.addOrUpdateNote(NoteMapper.createNoteFromDataWithId(id, name, description, priority, date, complete))
                        .compose(RxUtil.applySchedulers())
                        .subscribe(aBoolean -> {
                            if(aBoolean) {
                                getViewState().showToast("Completed");
                            } else {
                                getViewState().showToast("Error");
                            }
                        },Throwable::printStackTrace));
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
