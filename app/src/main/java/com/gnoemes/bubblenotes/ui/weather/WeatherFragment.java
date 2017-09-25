package com.gnoemes.bubblenotes.ui.weather;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gnoemes.bubblenotes.R;
import com.gnoemes.bubblenotes.ui.notes_list.NotesListAdapter;
import com.gnoemes.bubblenotes.ui.notes_list.NotesListFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kenji1947 on 25.09.2017.
 */

public class WeatherFragment extends Fragment{
    public static final String TAG = WeatherFragment.class.getSimpleName();
    @BindView(R.id.fab) FloatingActionButton fab;
    @BindView(R.id.listRecyclerView) RecyclerView listRecyclerView;
    DrawerLayout drawer_layout;
    @BindView(R.id.toolbar) Toolbar toolbar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes_list, container, false);
        ButterKnife.bind(this, view);

        drawer_layout = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);

        initToolbar();
        syncToolbarWithDrawer();

        return view;
    }

    private void initToolbar() {
        toolbar.setTitle("Weather list");
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
}
