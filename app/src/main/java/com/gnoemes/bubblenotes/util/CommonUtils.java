package com.gnoemes.bubblenotes.util;

import android.content.res.Resources;

import com.gnoemes.bubblenotes.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import timber.log.Timber;

/**
 * Created by kenji1947 on 28.09.2017.
 */

public class CommonUtils {

    private static List<String> priorityNames;

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

    public static List<String> getPriorityNames(Resources resources) {
        if (priorityNames == null) {
            String[] priStrings = resources.getStringArray(R.array.priority_array);
            priorityNames = new ArrayList<>();
            for (String s: priStrings) {
                priorityNames.add(s);
            }
        }
        return priorityNames;
    }
}
