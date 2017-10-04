package com.gnoemes.bubblenotes;

import android.view.View;
import android.widget.Button;

import org.junit.Test;

import io.reactivex.Observable;
import io.reactivex.functions.Cancellable;

/**
 * Created by kenji1947 on 04.10.2017.
 */

public class RxJavaInstrTest {
    Button button;

    @Test
    public void name() throws Exception {
        Observable observable = Observable.create(e -> {
            View.OnClickListener clickListener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    e.onNext(view.getId());
                }
            };
            Cancellable c = subscribe(clickListener);
            e.setCancellable(c);
        });
        button = new Button(null);
    }

    private Cancellable subscribe(View.OnClickListener clickListener) {
        button.setOnClickListener(clickListener);
        return new Cancellable() {
            @Override
            public void cancel() throws Exception {
            }
        };
    }
}
