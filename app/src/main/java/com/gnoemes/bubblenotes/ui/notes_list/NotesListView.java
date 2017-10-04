package com.gnoemes.bubblenotes.ui.notes_list;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.gnoemes.bubblenotes.repo.model.Description;
import com.gnoemes.bubblenotes.repo.model.Note;

import java.util.List;

/**
 * Created by kenji1947 on 28.09.2017.
 */

public interface NotesListView extends MvpView {
    @StateStrategyType(AddToEndSingleStrategy.class)
    void setNotesList(List<Note> notes);

    @StateStrategyType(SkipStrategy.class)
    void showMessage(String msg);
}
