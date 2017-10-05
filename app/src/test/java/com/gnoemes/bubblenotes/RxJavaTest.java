package com.gnoemes.bubblenotes;

import android.util.Log;

import org.junit.Test;
import org.junit.validator.PublicClassValidator;

import java.util.ArrayList;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by kenji1947 on 04.10.2017.
 */

public class RxJavaTest {
    static String TAG = "RxJavaTest";

    private void print(String s) {
        System.out.println(s);
    }
    @Test
    public void name() throws Exception {

        Observable.just("")
                .flatMap(s -> {
                    return Observable.just(new ArrayList<>(), new ArrayList<>());
                })
                .subscribe(new Observer<ArrayList<Object>>() {
                    Disposable disposable;

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        print("onSubscribe");
                        disposable = d;
                    }

                    @Override
                    public void onNext(@NonNull ArrayList<Object> objects) {
                       print("onNext");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                       print("onError");
                    }

                    @Override
                    public void onComplete() {
                        print("onComplete");
                    }
                });

        Disposable d = Observable.just("")
                .flatMap(s -> {
                    return Observable.just(new ArrayList<>(), new ArrayList<>());
                })
                .subscribe(objects -> {

                });
                
    }
    
    
    public void testObservableCreate() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {
            }
        });

        Observable.create(e -> {
        });
    }
    
    public void testMapFlatMap() {
        Observable.just("")
                .map(s -> {return new ArrayList<>();})
                .subscribe(r -> {});


        Observable.just("")
                .flatMap(s -> {
                    return Observable.just(new ArrayList<>(), new ArrayList<>());
                });
    }
}
