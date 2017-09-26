package com.gnoemes.bubblenotes.notes;


import com.gnoemes.bubblenotes.data.source.DataManager;
import com.gnoemes.bubblenotes.di.AppTestModule;
import com.gnoemes.bubblenotes.di.DataManagerTestModule;
import com.gnoemes.bubblenotes.di.PresenterTestComponent;
import com.gnoemes.bubblenotes.di.RealmTestModule;
import com.gnoemes.bubblenotes.di.RemoteTestModule;
import com.gnoemes.bubblenotes.ui.note_detail.NoteDetailPresenter;
import com.gnoemes.bubblenotes.ui.note_detail.NoteDetailView$$State;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.inject.Inject;

public class NoteListPresenterTest {

    PresenterTestComponent testComponent;

    @Inject
    DataManager dataManager;

    @Mock
    NoteDetailView$$State stateView;
    NoteDetailPresenter presenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        testComponent = com.gnoemes.bubblenotes.di.DaggerPresenterTestComponent.builder()
                    .appTestModule(new AppTestModule())
                    .networkModule(new RemoteTestModule())
                    .realmModule(new RealmTestModule())
                    .dataManagerModule(new DataManagerTestModule())
                    .build();
        testComponent.inject(this);

        presenter = new NoteDetailPresenter("1");
    }

    @Test
    public void checkManager_shouldNotNull() {
        Assert.assertNotNull(dataManager);
    }


}
