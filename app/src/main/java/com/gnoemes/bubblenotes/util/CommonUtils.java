package com.gnoemes.bubblenotes.util;

import java.util.concurrent.TimeUnit;

import timber.log.Timber;

/**
 * Created by kenji1947 on 28.09.2017.
 */

public class CommonUtils {

    public static void longOperation() {
        try {
            for (int i = 1; i < 3; i++) {
                Timber.d("Sleep " + i);
                TimeUnit.SECONDS.sleep(1);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
