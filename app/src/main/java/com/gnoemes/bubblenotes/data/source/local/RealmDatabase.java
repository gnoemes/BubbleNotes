package com.gnoemes.bubblenotes.data.source.local;

import android.content.Context;

import com.facebook.stetho.Stetho;
import com.gnoemes.bubblenotes.App;
import com.gnoemes.bubblenotes.data.model.Note;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class RealmDatabase {

    private RealmConfiguration config;

    @Inject
    Context mContext;


    public RealmDatabase() {
        App.getAppComponent().inject(this);
    }

    //TODO Stetho is not working. Wait for new version.
    public void setupStetho() {
        Stetho.initialize(
                Stetho.newInitializerBuilder(mContext)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(mContext))
                        .enableWebKitInspector(RealmInspectorModulesProvider.builder(mContext).build())
                        .build());
    }

    //TODO Take migration from other project. If db schema was changed, all data will be deleted!
    public void setupRealm() {
        Realm.init(mContext);
        if (config == null) {
            config = new RealmConfiguration
                    .Builder()
                    .deleteRealmIfMigrationNeeded()
                    .build();
            Realm.setDefaultConfiguration(config);
        } else {
            throw new IllegalStateException("database already configured");
        }
    }

    private void clearRealmDb() {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        Realm.getDefaultInstance().deleteAll();
        realm.commitTransaction();
    }

    private Realm getRealmInstance() {
        return Realm.getDefaultInstance();
    }

    public <T extends RealmObject> T add(T model) {
        getRealmInstance().executeTransactionAsync(realm1 ->
            realm1.copyToRealmOrUpdate(model)
        );
        return model;
    }

    public <T extends RealmObject> T findById(Class<T> clazz, String id) {
        //TODO Уникальный идентификатор для каждого типа объектов, получение данных в switch по идентификатору
        return getRealmInstance().where(clazz).equalTo(Note.ID,id).findFirst();
    }

    public <T extends RealmObject> RealmResults<T> findAll(Class<T> clazz) {
        //TODO Уникальный идентификатор для каждого типа объектов, получение данных в switch по идентификатору
        return getRealmInstance().where(clazz).findAllSortedAsync(Note.PRIORITY);
    }

    public void delete(Class<Note> clazz, String id) {
        getRealmInstance().executeTransaction(realm -> findById(clazz,id).deleteFromRealm());
    }
}
