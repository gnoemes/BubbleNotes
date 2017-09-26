package com.gnoemes.bubblenotes.di;

import com.gnoemes.bubblenotes.data.source.local.RealmDatabase;
import com.gnoemes.bubblenotes.data.source.local.RealmManager;
import com.gnoemes.bubblenotes.data.source.local.RealmManagerDefault;

import org.mockito.Mockito;

public class RealmTestModule extends RealmModule {
    @Override
    RealmDatabase provideRealmDatabase() {
        return Mockito.mock(RealmDatabase.class);
    }

    @Override
    RealmManager provideRealmManager() {
        return Mockito.mock(RealmManagerDefault.class);
    }
}
