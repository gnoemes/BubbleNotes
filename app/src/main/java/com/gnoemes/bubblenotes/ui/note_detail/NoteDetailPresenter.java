package com.gnoemes.bubblenotes.ui.note_detail;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.gnoemes.bubblenotes.App;
import com.gnoemes.bubblenotes.data.source.NoteRepository;
import com.gnoemes.bubblenotes.di.components.DaggerRepositoryComponent;
import com.gnoemes.bubblenotes.di.components.RepositoryComponent;
import com.gnoemes.bubblenotes.utils.NoteMapper;
import com.gnoemes.bubblenotes.utils.RxUtil;

import javax.inject.Inject;

import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by kenji1947 on 25.09.2017.
 */

@InjectViewState
public class NoteDetailPresenter extends MvpPresenter<NoteDetailView> {
    private CompositeDisposable subscriptions = new CompositeDisposable();
    private NoteRepository repository;

    private String id;

    @Inject
    public NoteDetailPresenter(NoteRepository repository, String id) {
        this.repository = repository;
        this.id = id;
    }

    @Override
    protected void onFirstViewAttach() {
        Timber.d("onFirstViewAttach id: " + id);
        initComponent();
        //TODO Is in edit mode
        if (id != null)
            getNote(id);
    }

    private void initComponent() {
        RepositoryComponent repositoryComponent = DaggerRepositoryComponent.builder()
                .appComponent(App.getAppComponent())
                .build();
        repositoryComponent.inject(this);
    }

    public void getNote(String id) {
        subscriptions.add(repository.loadNoteById(id)
                    .compose(RxUtil.applyFlowableSchedulers())
                    .subscribe(note -> getViewState().setNote(note)));

    }

    public void addNote(String id,String name, int priority) {
            repository.addOrUpdateNote(NoteMapper.createNoteFromData(id,name,priority))
//                    .filter(note -> note.isLoaded())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new CompletableObserver() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {

                        }

                        @Override
                        public void onComplete() {
                            getViewState().showToast("Note saved ");
                            getViewState().backPressed();
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            e.printStackTrace();
                            getViewState().showToast("Error when saving");
                        }
                    });
    }

    public void updateNote(String id, String name, int priority) {
       repository.addOrUpdateNote((NoteMapper.createNoteFromData(id,name,priority)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        getViewState().showToast("Note updated");
                        getViewState().backPressed();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        e.printStackTrace();
                        getViewState().showToast("Error when updating");
                    }
                });
    }
    public void deleteNote(String id) {

        subscriptions.add(repository.deleteNote(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(() -> {
                        getViewState().showToast("Note deleted");
                        getViewState().backPressed();
                    }, Throwable::printStackTrace));
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
