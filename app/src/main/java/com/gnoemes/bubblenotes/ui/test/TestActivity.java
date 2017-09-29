package com.gnoemes.bubblenotes.ui.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.NumberPicker;

import com.gnoemes.bubblenotes.R;

import butterknife.BindView;

/**
 * Created by kenji1947 on 29.09.2017.
 */

public class TestActivity extends AppCompatActivity {
    @BindView(R.id.numberPicker) NumberPicker numberPicker;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        numberPicker = new NumberPicker(this);
    }
}
