package com.gnoemes.bubblenotes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      
        int count = countSomething();
    }

    private void test() {

    }
    
    private int countSomething() {
        int count = 0;
        for (int i = 0; i < 33; i++) {
            count++;
        }
        return count;

    }
}
