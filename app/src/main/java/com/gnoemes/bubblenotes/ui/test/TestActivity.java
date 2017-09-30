package com.gnoemes.bubblenotes.ui.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.NumberPicker;
import android.widget.Spinner;

import com.gnoemes.bubblenotes.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.srodrigo.androidhintspinner.HintAdapter;
import me.srodrigo.androidhintspinner.HintSpinner;
import timber.log.Timber;

/**
 * Created by kenji1947 on 29.09.2017.
 */

public class TestActivity extends AppCompatActivity {
    @BindView(R.id.spinner) Spinner spinner;
    HintSpinner<String> defaultHintSpinner;
    @BindView(R.id.nameTextInput)
    TextInputLayout nameTextInput;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);



        initSpinner();
        initTextInput();
    }

    private void initTextInput() {
        nameTextInput.setHint("Enter something");
    }

    private void initSpinner() {
        List<String> priorityList = new ArrayList<>();
        priorityList.add("High");
        priorityList.add("Medium");
        priorityList.add("Low");

        defaultHintSpinner = new HintSpinner<>(spinner,
                new HintAdapter<String>(this, R.string.app_name, priorityList),
                new HintSpinner.Callback<String>() {
                    @Override
                    public void onItemSelected(int position, String itemAtPosition) {
                        // Here you handle the on item selected event (this skips the hint selected event)
                    }
                });
        defaultHintSpinner.init();
    }
}
