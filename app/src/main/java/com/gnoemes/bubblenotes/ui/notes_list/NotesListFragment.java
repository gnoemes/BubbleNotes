package com.gnoemes.bubblenotes.ui.notes_list;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.util.DiffUtil;
import android.support.v7.util.ListUpdateCallback;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.gnoemes.bubblenotes.App;
import com.gnoemes.bubblenotes.R;
import com.gnoemes.bubblenotes.repo.local.LocalRepositoryImpl;
import com.gnoemes.bubblenotes.repo.local.RepoDi;
import com.gnoemes.bubblenotes.repo.model.Comment;
import com.gnoemes.bubblenotes.repo.model.Description;
import com.gnoemes.bubblenotes.repo.model.Note;
import com.gnoemes.bubblenotes.ui.note_detail.NoteDetailActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.objectbox.Box;
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
                Schedulers.io(), RepoDi.getLocalRepo(boxStore));
    }

    NotesListAdapter adapterRecycler;
    NotesListAdapter.ItemClickListener adapterClickListener = new NotesListAdapter.ItemClickListener() {
        @Override
        public void onClick(Long id) {
            Timber.d("onClick" + id);
            Intent intent = new Intent(getActivity(), NoteDetailActivity.class);
            intent.putExtra(NoteDetailActivity.EXTRA_NOTE_ID, id);
            startActivity(intent);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes_list, container, false);
        ButterKnife.bind(this, view);

        fab.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), NoteDetailActivity.class);
            startActivity(intent);
        });

        drawer_layout = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);

        listRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //listRecyclerView.setNestedScrollingEnabled(false);

        adapterRecycler = new NotesListAdapter(new ArrayList<>(0), adapterClickListener);

        listRecyclerView.setAdapter(adapterRecycler);

        initToolbar();
        syncToolbarWithDrawer();
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.noteslist_fragment_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Box<Note> noteBox = boxStore.boxFor(Note.class);
        Box<Description> descriptionBox = boxStore.boxFor(Description.class);
        Box<Comment> commentBox = boxStore.boxFor(Comment.class);

        List<Note> notes = noteBox.getAll();
        List<Description> descriptions = descriptionBox.getAll();
        List<Comment> comments = commentBox.getAll();

        switch (item.getItemId()) {
            case R.id.menu_log_note:
                Timber.d("LOG Notes");
                for (Note note: notes) {
                    Timber.d("NOTE id:" + note.getId() + " name:" + note.getName()
                            + " DESCRIPTION id:" + note.getDescription().getTarget().getId()
                            + " name:" + note.getDescription().getTarget().getName()
                            + " priority:" + note.getDescription().getTarget().getPriority());
                    for (Comment comment: note.getComments()) {
                        Timber.d("COMMENT id:" + comment.getId());
                    }
                }
                return true;

            case R.id.menu_log_description:
                Timber.d("LOG Description");
                for (Description desc : descriptions) {
                    Timber.d("DESCRIPTION id:" + desc.getId()
                            + " name:" + desc.getName() + " priority:" + desc.getPriority());
                }

                return true;

            case R.id.menu_log_comment:
                Timber.d("LOG Comment");
                for (Comment comment : comments) {
                    Timber.d("COMMENT id:" + comment.getId() + " body:" + comment.getBody());
                }
                return true;

            case R.id.menu_update_description:
                Timber.d("Button changeDescription");
                Description description = descriptions.get(1);
                description.setPriority(1);
                description.setName("Name from frag");
                descriptionBox.put(description);

                Description newDesc = descriptionBox.get(description.getId());


                return true;
            case R.id.menu_update_comment:

                return true;

            case R.id.menu_delete_all:
                Timber.d("Delete all");
                noteBox.removeAll();
                commentBox.removeAll();;
                descriptionBox.removeAll();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
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

        //TODO Method priority matters?
        //TODO Доделать DiffUtil. Выгрузить в фоновый поток.
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new NotesDiff(notes, adapterRecycler.getData()));
        adapterRecycler.updateData(notes);
        diffResult.dispatchUpdatesTo(adapterRecycler);

    }

    @Override
    public void notifyDescriptionChanged(List<Description> descriptions) {
        Timber.d("notifyDescriptionChanged");

    }

    public void notifyCommentsChanged(List<Comment> comments) {
        Timber.d("notifyCommentsChanged");

    }

    @Override
    public void showMessage(String msg) {
        Timber.d("showMessage " + msg);
    }
}
