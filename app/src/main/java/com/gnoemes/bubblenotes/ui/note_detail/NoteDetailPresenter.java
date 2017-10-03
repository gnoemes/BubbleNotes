package com.gnoemes.bubblenotes.ui.note_detail;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.gnoemes.bubblenotes.App;
import com.gnoemes.bubblenotes.data.note.local.NoteDataSource;
import com.gnoemes.bubblenotes.di.components.DaggerRepositoryComponent;
import com.gnoemes.bubblenotes.di.components.RepositoryComponent;
import com.gnoemes.bubblenotes.utils.NoteMapper;
import com.gnoemes.bubblenotes.utils.RxUtil;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

/**
 * Created by kenji1947 on 25.09.2017.
 */

@InjectViewState
public class NoteDetailPresenter extends MvpPresenter<NoteDetailView> {
    private CompositeDisposable subscriptions = new CompositeDisposable();
    private NoteDataSource repository;

    private long id;

    @Inject
    public NoteDetailPresenter(NoteDataSource repository, long id) {
        this.repository = repository;
        this.id = id;
    }

    @Override
    protected void onFirstViewAttach() {
        Timber.d("onFirstViewAttach id: " + id);
        initComponent();
        //TODO Is in edit mode
        if (id != 0)
            getNote(id);
    }

    private void initComponent() {
        RepositoryComponent repositoryComponent = DaggerRepositoryComponent.builder()
                .appComponent(App.getAppComponent())
                .build();
        repositoryComponent.inject(this);
    }

    public void getNote(long id) {
        subscriptions.add(repository.loadNoteById(id)
                    .compose(RxUtil.applyFlowableSchedulers())
                    .subscribe(note -> getViewState().setNote(note)));

    }

    public void addNote(String name,String description, int priority, String date) {
           subscriptions.add(repository.addOrUpdateNote(NoteMapper.createNoteFromData(name,description,priority,date,false))
                   .compose(RxUtil.applySchedulers())
                   .subscribe(aBoolean -> {
                       if (aBoolean) {
                           getViewState().showToast("Note saved ");
                           getViewState().backPressed();
                       }
                       else {
                           getViewState().showToast("Error when saving");
                       }
                   },Throwable::printStackTrace));
    }

    public void updateNote(long id, String name,String description, int priority,String date) {
        subscriptions.add(repository.addOrUpdateNote((NoteMapper.createNoteFromDataWithId(id,name,description,priority,date,false)))
                .compose(RxUtil.applySchedulers())
                .subscribe(aBoolean -> {
                    if (aBoolean) {
                        getViewState().showToast("Note updated");
                        getViewState().backPressed();
                    }
                    else {
                        getViewState().showToast("Error when updating");
                    }
                },Throwable::printStackTrace));

    }
    public void deleteNote(long id) {
      subscriptions.add(repository.deleteNote(id)
                .compose(RxUtil.applySchedulers())
                .subscribe(aBoolean -> {
                    if (aBoolean) {
                        getViewState().showToast("Note deleted");
                        getViewState().backPressed();
                    } else  {
                        getViewState().showToast("Error");
                    }
                },Throwable::printStackTrace));
    }

    void onStop() {
        Timber.d("onStop");

    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        RxUtil.unsubscribe(subscriptions);
    }
}
