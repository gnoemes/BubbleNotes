package com.gnoemes.bubblenotes.ui.notes_list;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.gnoemes.bubblenotes.model.Note;

import io.realm.OrderedCollectionChangeSet;
import io.realm.RealmResults;
import io.realm.rx.CollectionChange;

/**
 * Created by kenji1947 on 25.09.2017.
 */

public interface NotesListView extends MvpView {

    @StateStrategyType(AddToEndSingleStrategy.class)
    void setNotesList(RealmResults<Note> notes);

    @StateStrategyType(SkipStrategy.class)
    void setChangeSet(OrderedCollectionChangeSet orderedCollectionChangeSet);
}
