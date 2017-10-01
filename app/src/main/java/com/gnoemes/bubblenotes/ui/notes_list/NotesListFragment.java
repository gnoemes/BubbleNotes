package com.gnoemes.bubblenotes.ui.notes_list;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.gnoemes.bubblenotes.App;
import com.gnoemes.bubblenotes.R;
import com.gnoemes.bubblenotes.repo.local.LocalRepositoryImpl;
import com.gnoemes.bubblenotes.repo.model.Description;
import com.gnoemes.bubblenotes.repo.model.Note;
import com.gnoemes.bubblenotes.ui.note_detail.NoteDetailActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.objectbox.BoxStore;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by kenji1947 on 28.09.2017.
 */

public class NotesListFragment extends MvpAppCompatFragment implements NotesListView {
    public static final String TAG = NotesListFragment.class.getSimpleName();

    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.listRecyclerView)
    RecyclerView listRecyclerView;
    DrawerLayout drawer_layout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @InjectPresenter
    NotesListPresenter presenter;

    //@Inject
    BoxStore boxStore;

    List<Note> notes;

    @ProvidePresenter
    NotesListPresenter providePresenter() {
//        App.getAppComponent().inject(this);
        boxStore = ((App) (getActivity().getApplication())).getBoxStore();
        return new NotesListPresenter(AndroidSchedulers.mainThread(),
                Schedulers.io(), new LocalRepositoryImpl(boxStore));
    }

    NotesListAdapter adapterRecycler;
    NotesListAdapter.ItemClickListener adapterClickListener = new NotesListAdapter.ItemClickListener() {
        @Override
        public void onClick(Long id) {
            Timber.d("onClick" + id);

//            Note note = new Note();
//            note.setName("name");
//
//            Description description = new Description();
//            description.setName("desc");
//
//            note.getDescription().setTarget(description);
//
//            presenter.addOrUpdateNote(note);

            Intent intent = new Intent(getActivity(), NoteDetailActivity.class);
            intent.putExtra(NoteDetailActivity.EXTRA_NOTE_ID, id);
            startActivity(intent);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes_list, container, false);
        ButterKnife.bind(this, view);

        fab.setOnClickListener(view1 -> {

//            if (notes.get(3).getDescription().getTarget() == null) {
//                Description description = new Description();
//                description.setName(UUID.randomUUID().toString());
//                notes.get(3).getDescription().setTarget(description);
//            } else {
//                notes.get(3).getDescription().getTarget().setName(UUID.randomUUID().toString());
//
//            }
//            notes.get(3).getDescription().getTarget().setName("awesome desc");
//            presenter.addOrUpdateNote(notes.get(3));

            Intent intent = new Intent(getActivity(), NoteDetailActivity.class);
            startActivity(intent);
        });

        drawer_layout = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);

        listRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapterRecycler = new NotesListAdapter(new ArrayList<>(0), adapterClickListener);

        listRecyclerView.setAdapter(adapterRecycler);

        initToolbar();
        syncToolbarWithDrawer();
        return view;
    }

    private void initToolbar() {
        toolbar.setTitle("Notes list");
        AppCompatActivity activity = (AppCompatActivity)getActivity();
        activity.setSupportActionBar(toolbar);
        setHasOptionsMenu(true);
    }

    private void syncToolbarWithDrawer() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                getActivity(), drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer_layout.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (!getActivity().isChangingConfigurations()) {
            presenter.onStop();
        }
    }


    @Override
    public void setNotesList(List<Note> notes) {
        Timber.d("setNotesList");
        this.notes = notes;
        adapterRecycler.updateData(notes);
    }

    @Override
    public void notifyDescriptionChanged(List<Description> descriptions) {
        Timber.d("notifyDescriptionChanged");
    }

    @Override
    public void showMessage(String msg) {
        Timber.d("showMessage " + msg);
    }
}
