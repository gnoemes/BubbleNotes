package com.gnoemes.bubblenotes;

import org.junit.Test;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by kenji1947 on 04.10.2017.
 */

public class Launcher {

    @Test
    public void main() {
        Flowable.range(1, 999_999_999)
                .map(MyItem::new)
                .observeOn(Schedulers.io())
                .subscribe(myItem -> {
                    sleep(50);
                    System.out.println("Received MyItem " + myItem.id);
                });
        sleep(Long.MAX_VALUE);
    }

    static void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static final class MyItem {
        final int id;

        MyItem(int id) {
            this.id = id;
            System.out.println("Constructing MyItem " + id);
        }
    }
}

