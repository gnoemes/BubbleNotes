package com.gnoemes.bubblenotes.ui.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.gnoemes.bubblenotes.R;
import com.gnoemes.bubblenotes.ui.notes_list.NotesListFragment;
import com.gnoemes.bubblenotes.ui.weather.WeatherFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by kenji1947 on 30.07.2017.
 */

public class MainDrawerActivity extends
        AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.drawer_layout) DrawerLayout drawer_layout;
    @BindView(R.id.navigation_view) NavigationView navigation_view;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_drawer);
        ButterKnife.bind(this);

        //launch fragment at first start
        if (savedInstanceState == null) {
            //showFragment(ImportFragment.class, ImportFragment.TAG);
            replaceFragment(NotesListFragment.class, NotesListFragment.TAG);
            navigation_view.setCheckedItem(R.id.nav_notes);
        }
        navigation_view.setNavigationItemSelectedListener(this);
    }

    private void replaceFragment(Class clazz, String TAG) {
        Fragment fragment = null;
        try {
            fragment = (Fragment) clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, fragment, TAG).commit();

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        //todo Move toggle.syncState() here
    }

    @Override
    public void onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_notes:
                Timber.d("nav_notes");
                replaceFragment(NotesListFragment.class, NotesListFragment.TAG);
                break;
            case R.id.nav_weather:
                Timber.d("nav_notes");
                replaceFragment(WeatherFragment.class, WeatherFragment.TAG);
                break;
            case R.id.nav_settings:
                Timber.d("nav_settings");
                replaceFragment(com.gnoemes.bubblenotes.ui_box.notes_list.NotesListFragment.class,
                        com.gnoemes.bubblenotes.ui_box.notes_list.NotesListFragment.TAG);
                break;
            case R.id.nav_about:
                Timber.d("nav_about");
                break;
        }
        drawer_layout.closeDrawer(GravityCompat.START);
        return true;
    }
}
