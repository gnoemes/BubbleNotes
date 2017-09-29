package com.gnoemes.bubblenotes.util;


import com.gnoemes.bubblenotes.utils.RxUtil;

import org.mockito.BDDMockito;
import org.powermock.api.mockito.PowerMockito;

import io.reactivex.schedulers.Schedulers;

public class RxTestUtil {

    public static void mockRxSchedulers() {
        PowerMockito.mockStatic(RxUtil.class);
        BDDMockito.given(RxUtil.applyFlowableSchedulers())
                .willReturn(observable ->
                        observable.subscribeOn(Schedulers.trampoline())
                                .observeOn(Schedulers.trampoline()));
        BDDMockito.given(RxUtil.applyMaybeSchedulers())
                .willReturn(observable ->
                        observable.subscribeOn(Schedulers.trampoline())
                                .observeOn(Schedulers.trampoline()));
        BDDMockito.given(RxUtil.applySchedulers())
                .willReturn(observable ->
                        observable.subscribeOn(Schedulers.trampoline())
                                .observeOn(Schedulers.trampoline()));
        BDDMockito.given(RxUtil.applySingleSchedulers())
                .willReturn(observable ->
                        observable.subscribeOn(Schedulers.trampoline())
                                .observeOn(Schedulers.trampoline()));
    }
}
