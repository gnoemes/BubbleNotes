package com.gnoemes.bubblenotes.ui.note_detail;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.gnoemes.bubblenotes.App;
import com.gnoemes.bubblenotes.R;
import com.gnoemes.bubblenotes.data.model.Note;
import com.gnoemes.bubblenotes.data.source.DataManager;

import java.util.UUID;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import timber.log.Timber;

/**
 * Created by kenji1947 on 25.09.2017.
 */

public class NoteDetailActivity extends MvpAppCompatActivity implements NoteDetailView{
    public final static String EXTRA_NOTE_ID = "note_id";

    private boolean isInEditMode;
    private String note_id;

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.progressBar) ProgressBar progressBar;
    @BindView(R.id.nameEditText) EditText nameEditText;
    @BindView(R.id.priorityEditText) EditText priorityEditText;
    @BindView(R.id.idTextView) TextView idTextView;
    @BindView(R.id.fab) FloatingActionButton fab;
    @BindView(R.id.fabDelete) FloatingActionButton fabDelete;

    @Inject
    DataManager dataManager;

    @InjectPresenter NoteDetailPresenter presenter;

    @ProvidePresenter
    NoteDetailPresenter providePresenter() {
        App.getAppComponent().inject(this);

        return new NoteDetailPresenter(Realm.getDefaultInstance(),
                AndroidSchedulers.mainThread(),
                Schedulers.io(),
                dataManager,
                getIntent().getStringExtra(EXTRA_NOTE_ID));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);

        Timber.d("onCreate");
        ButterKnife.bind(this);

        note_id = getIntent().getStringExtra(EXTRA_NOTE_ID);
        isInEditMode = note_id != null;

        disableKeyboardOnStart();
        initToolbar();
        initSaveButton();
        initDeleteButton();
    }

    private void initDeleteButton() {
        fabDelete.setOnClickListener(view1 -> {
            if (note_id != null)
                presenter.deleteNote(note_id);
        });
    }

    private void initSaveButton() {
        if (isInEditMode) {
            fab.setOnClickListener((v) -> updateNote());
        }
        else {
            fab.setOnClickListener((v) -> addNote());
        }
    }

    private void updateNote() {
        presenter.updateNote(idTextView.getText().toString(),
                nameEditText.getText().toString(),
                Integer.parseInt(priorityEditText.getText().toString()));
    }

    private void addNote() {
        presenter.addNote(UUID.randomUUID().toString(), nameEditText.getText().toString(), Integer.parseInt(priorityEditText.getText().toString()));
    }



    private void disableKeyboardOnStart() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    private void initToolbar() {
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            if (isInEditMode)
                getSupportActionBar().setTitle("Edit Note: " + note_id);
            else
                getSupportActionBar().setTitle("New Note");
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (!isChangingConfigurations()) {
            presenter.onStop();
        }
    }

    @Override
    public void setProgressIndicator(boolean refreshing) {
        Timber.d("setProgressIndicator " + refreshing);
        if (refreshing) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void setNote(Note note) {
        idTextView.setText(note.getId());
        nameEditText.setText(note.getName());
        priorityEditText.setText(note.getPriority() + "");
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void backPressed() {
        onBackPressed();
    }
}
