package com.gnoemes.bubblenotes.di;

import com.gnoemes.bubblenotes.data.source.remote.RemoteManager;
import com.gnoemes.bubblenotes.data.source.remote.RemoteManagerDefault;

import org.mockito.Mockito;

public class RemoteTestModule extends NetworkModule {

    @Override
    RemoteManager provideRemoteDataManager() {
        return Mockito.mock(RemoteManagerDefault.class);
    }
}
