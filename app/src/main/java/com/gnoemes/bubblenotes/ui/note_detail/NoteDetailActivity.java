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
import com.gnoemes.bubblenotes.data.note.model.Note;
import com.gnoemes.bubblenotes.utils.DateUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by kenji1947 on 25.09.2017.
 */

public class NoteDetailActivity extends MvpAppCompatActivity implements NoteDetailView{
    public final static String EXTRA_NOTE_ID = "note_id";

    private boolean isInEditMode;
    private long note_id;

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.progressBar) ProgressBar progressBar;
    @BindView(R.id.nameEditText) EditText nameEditText;
    @BindView(R.id.priorityEditText) EditText priorityEditText;
    @BindView(R.id.fab) FloatingActionButton fab;
    @BindView(R.id.fabDelete) FloatingActionButton fabDelete;
    @BindView(R.id.actionType) TextView actionTypeTextView;
    @BindView(R.id.dateTextView) TextView dateTextView;
    @BindView(R.id.descriptionEditText) EditText descriptionEditText;

    @InjectPresenter
    NoteDetailPresenter presenter;
    @ProvidePresenter
    NoteDetailPresenter providePresenter() {
        return new NoteDetailPresenter(App.getAppComponent().getNoteRepository(),getIntent().getLongExtra(EXTRA_NOTE_ID,0));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);
        ButterKnife.bind(this);

        note_id = getIntent().getLongExtra(EXTRA_NOTE_ID,0);
        isInEditMode = note_id != 0;

        disableKeyboardOnStart();
        initToolbar();
        initSaveButton();
        initDeleteButton();
    }

    private void initDeleteButton() {
        fabDelete.setOnClickListener(view1 -> {
            if (note_id != 0)
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
        presenter.updateNote(note_id,nameEditText.getText().toString(),descriptionEditText.getText().toString(),
                Integer.parseInt(priorityEditText.getText().toString()),DateUtil.getCurrentDate());
    }

    private void addNote() {
        String date = DateUtil.getCurrentDate();
        presenter.addNote(nameEditText.getText().toString(),descriptionEditText.getText().toString(), Integer.parseInt(priorityEditText.getText().toString()),date);
    }



    private void disableKeyboardOnStart() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    private void initToolbar() {
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            if (isInEditMode) {
                nameEditText.setFocusable(false);
                actionTypeTextView.setText(R.string.msg_action_edit);
//                getSupportActionBar().setTitle("Edit Note: " + note_id);
            }
            else {
                actionTypeTextView.setText(R.string.msg_action_create);
                nameEditText.setFocusable(true);
//                getSupportActionBar().setTitle("New Note");
            }
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
        nameEditText.setText(note.getName());
        priorityEditText.setText(String.valueOf(note.getPriority()));
        descriptionEditText.setText(note.getDescription());
        dateTextView.setText(note.getDate());
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
