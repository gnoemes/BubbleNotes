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
import com.gnoemes.bubblenotes.R;
import com.gnoemes.bubblenotes.data.model.Note;
import com.gnoemes.bubblenotes.ui.note_detail.NoteDetailActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.OrderedCollectionChangeSet;
import io.realm.Realm;
import io.realm.RealmResults;
import timber.log.Timber;

/**
 * Created by kenji1947 on 25.09.2017.
 */

public class NotesListFragment extends MvpAppCompatFragment implements NotesListView{
    public static final String TAG = NotesListFragment.class.getSimpleName();

    @BindView(R.id.fab) FloatingActionButton fab;
    @BindView(R.id.listRecyclerView) RecyclerView listRecyclerView;
    DrawerLayout drawer_layout;
    @BindView(R.id.toolbar) Toolbar toolbar;

    @InjectPresenter
    NotesListPresenter presenter;
    @ProvidePresenter
    NotesListPresenter providePresenter() {
        return new NotesListPresenter(Realm.getDefaultInstance());
    }

    NotesListAdapter adapter;
    NotesListAdapter.ItemClickListener adapterClickListener = new NotesListAdapter.ItemClickListener() {
        @Override
        public void onClick(String note_id) {
            Timber.d("onClick" + note_id);
            Intent intent = new Intent(getActivity(), NoteDetailActivity.class);
            intent.putExtra(NoteDetailActivity.EXTRA_NOTE_ID, note_id);
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
            //presenter.addNote("defsdf", 3);
            Intent intent = new Intent(getActivity(), NoteDetailActivity.class);
            startActivity(intent);
        });

        drawer_layout = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);

        listRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new NotesListAdapter(null, adapterClickListener);
        listRecyclerView.setAdapter(adapter);

        initToolbar();
        //TODO Объяснить
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
    public void setNotesList(RealmResults<Note> notes) {
        adapter.updateData(notes);
    }

    //This method not used yet
    @Override
    public void setChangeSet(OrderedCollectionChangeSet orderedCollectionChangeSet) {
        //уведомить адаптер
        notifyAdapter(orderedCollectionChangeSet);
    }
    //This method not used yet
    private void notifyAdapter(OrderedCollectionChangeSet changeSet) {
        // For deletions, the adapter has to be notified in reverse order.
        if (changeSet == null) {
            adapter.notifyDataSetChanged();
            return;
        }

        OrderedCollectionChangeSet.Range[] deletions = changeSet.getDeletionRanges();
        for (int i = deletions.length - 1; i >= 0; i--) {
            OrderedCollectionChangeSet.Range range = deletions[i];
            adapter.notifyItemRangeRemoved(range.startIndex, range.length);
        }

        OrderedCollectionChangeSet.Range[] insertions = changeSet.getInsertionRanges();
        for (OrderedCollectionChangeSet.Range range : insertions) {
            adapter.notifyItemRangeInserted(range.startIndex, range.length);
        }

        OrderedCollectionChangeSet.Range[] modifications = changeSet.getChangeRanges();
        for (OrderedCollectionChangeSet.Range range : modifications) {
            adapter.notifyItemRangeChanged(range.startIndex, range.length);
        }
    }
}
