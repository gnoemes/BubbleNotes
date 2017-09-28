package com.gnoemes.bubblenotes;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by kenji1947 on 28.09.2017.
 */

public class Injector {
    private static RealmConfiguration config;
    private static Realm realm;

    public static RealmConfiguration getConfig() {
        if (config == null)
            config = new RealmConfiguration.Builder().inMemory().name("test-realm").build();
        return config;
    }

    public static Realm getInMemoryRealm() {
        if (realm == null) {
            Realm.setDefaultConfiguration(getConfig());
            realm = Realm.getDefaultInstance();
        }
        if (realm.isClosed())
            realm = Realm.getDefaultInstance();

        realm.executeTransaction(realm1 -> {
            realm1.deleteAll();
        });
        return realm;
    }

    //TODO Тесты должны получать инстанс из мейн потока
}
