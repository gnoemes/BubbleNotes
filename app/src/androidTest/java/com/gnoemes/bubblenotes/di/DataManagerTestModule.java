package com.gnoemes.bubblenotes.di;


import com.gnoemes.bubblenotes.data.source.DataManager;
import com.gnoemes.bubblenotes.data.source.DataManagerDefault;
import com.gnoemes.bubblenotes.data.source.local.RealmManager;
import com.gnoemes.bubblenotes.data.source.remote.RemoteManager;

import org.mockito.Mockito;

public class DataManagerTestModule extends DataManagerModule{

    @Override
    DataManager provideDataManager(RealmManager realmManager, RemoteManager remoteDataManager) {
        return Mockito.mock(DataManagerDefault.class);
    }
}
