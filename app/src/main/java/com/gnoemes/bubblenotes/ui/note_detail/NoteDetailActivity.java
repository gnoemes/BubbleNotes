package com.gnoemes.bubblenotes.ui.note_detail;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.gnoemes.bubblenotes.App;
import com.gnoemes.bubblenotes.R;
import com.gnoemes.bubblenotes.repo.local.LocalRepositoryImpl;
import com.gnoemes.bubblenotes.repo.model.Comment;
import com.gnoemes.bubblenotes.repo.model.Description;
import com.gnoemes.bubblenotes.repo.model.Note;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import io.objectbox.BoxStore;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.srodrigo.androidhintspinner.HintAdapter;
import me.srodrigo.androidhintspinner.HintSpinner;
import timber.log.Timber;

/**
 * Created by kenji1947 on 25.09.2017.
 */

public class NoteDetailActivity extends MvpAppCompatActivity implements NoteDetailView{
    public final static String EXTRA_NOTE_ID = "note_id";

    private boolean isInEditMode;
    private long note_id;
    Note note;

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.progressBar) ProgressBar progressBar;
    @BindView(R.id.idTextView) TextView idTextView;
    @BindView(R.id.fab) FloatingActionButton fab;
    @BindView(R.id.fabDelete) FloatingActionButton fabDelete;
    @BindView(R.id.nameEditText) EditText nameEditText;
    @BindView(R.id.descriptionEditText) EditText descriptionEditText;
    @BindView(R.id.prioritySpinner) Spinner prioritySpinner;
    @BindView(R.id.completeCheckBox) CheckBox completeCheckBox;

    HintSpinner<String> defaultHintSpinner;

    @InjectPresenter
    NoteDetailPresenter presenter;

    BoxStore boxStore;

    @ProvidePresenter
    NoteDetailPresenter providePresenter() {
        boxStore = ((App)(getApplication())).getBoxStore();
        return new NoteDetailPresenter(
                AndroidSchedulers.mainThread(),
                Schedulers.io(),
                new LocalRepositoryImpl(boxStore), getIntent().getLongExtra(EXTRA_NOTE_ID, -1));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);

        Timber.d("onCreate");
        ButterKnife.bind(this);

        note_id = getIntent().getLongExtra(EXTRA_NOTE_ID, -1);
        isInEditMode = note_id != -1;

        disableKeyboardOnStart();
        initToolbar();
        initSaveButton();
        initDeleteButton();
        initSpinner();
    }

    private void initSpinner() {
        List<String> priorityList = new ArrayList<>();
        priorityList.add("High");
        priorityList.add("Medium");
        priorityList.add("Low");

        defaultHintSpinner = new HintSpinner<>(prioritySpinner,
                new HintAdapter<String>(this, R.string.app_name, priorityList),
                new HintSpinner.Callback<String>() {
                    @Override
                    public void onItemSelected(int position, String itemAtPosition) {
                        Timber.d("prioritySpinner.getSelectedItemPosition() " + prioritySpinner.getSelectedItemPosition());
                        // Here you handle the on item selected event (this skips the hint selected event)
                    }
                });
        defaultHintSpinner.init();
    }

    private void initDeleteButton() {
        fabDelete.setOnClickListener(view1 -> {
            if (note_id != -1);
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
        note.setName(nameEditText.getText().toString());
        note.setComplete(completeCheckBox.isChecked());

        //description
        note.getDescription().getTarget().setName(descriptionEditText.getText().toString());
        note.getDescription().getTarget().setPriority(prioritySpinner.getSelectedItemPosition());

        //adapter get data
        //note.getComments().

        presenter.addOrUpdateNote(note);
    }

    private void addNote() {
        Note note = new Note();
        //Todo Обязательное
        note.setName(nameEditText.getText().toString());
        note.setComplete(completeCheckBox.isChecked());

        //description fill
        Description description = new Description();
        note.getDescription().setTarget(description);
        description.setPriority(prioritySpinner.getSelectedItemPosition());
        description.setName(descriptionEditText.getText().toString());

        //comments fill
        List<Comment> comments = new ArrayList<>();
        note.getComments().addAll(comments);

        presenter.addOrUpdateNote(note);
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
            //presenter.onStop();
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
        this.note = note;
        idTextView.setText(note.getId() + "");
        nameEditText.append(note.getName());
        completeCheckBox.setChecked(note.isComplete());

        descriptionEditText.append(note.getDescription().getTarget().getName());
        prioritySpinner.setSelection(note.getDescription().getTarget().getPriority());

        //adapter
        //note.getComments().get(1).getBody();
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
