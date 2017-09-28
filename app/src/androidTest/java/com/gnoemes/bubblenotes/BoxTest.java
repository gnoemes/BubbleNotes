package com.gnoemes.bubblenotes;

import com.gnoemes.bubblenotes.repo_box.model.MyObjectBox;

import org.junit.Before;

import io.objectbox.BoxStore;

/**
 * Created by kenji1947 on 28.09.2017.
 */

public class BoxTest {

    @Before
    public void setUp() throws Exception {
        BoxStore boxStore = MyObjectBox.builder().build();
    }

}
