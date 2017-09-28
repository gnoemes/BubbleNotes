package com.gnoemes.bubblenotes.ui.notes_list;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.gnoemes.bubblenotes.data.model.Note;

import java.util.List;

import io.realm.OrderedCollectionChangeSet;

/**
 * Created by kenji1947 on 25.09.2017.
 */

public interface NotesListView extends MvpView {

    @StateStrategyType(AddToEndSingleStrategy.class)
    void setNotesList(List<Note> notes);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void setChangeSet(OrderedCollectionChangeSet orderedCollectionChangeSet);
}
