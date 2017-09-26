package com.gnoemes.bubblenotes.realm;

import com.gnoemes.bubblenotes.data.model.Note;
import com.gnoemes.bubblenotes.data.source.local.RealmDatabase;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.junit.Assert.*;
public class RealmDatabaseTest {

    @Mock
    RealmDatabase realm;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        realm.setupRealm();
    }

    @Test
    public void shouldBeAbleToCreateARealmObject() {
       Note note = new Note();
        note.setId("1");
        when(realm.add(note)).thenReturn(note);

        Note output = realm.findById(Note.class,"1");
        assertThat(output,is(note));
    }


}
