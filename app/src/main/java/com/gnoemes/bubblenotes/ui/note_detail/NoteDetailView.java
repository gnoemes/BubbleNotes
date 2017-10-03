package com.gnoemes.bubblenotes.ui.note_detail;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.gnoemes.bubblenotes.data.note.model.Note;

/**
 * Created by kenji1947 on 25.09.2017.
 */

public interface NoteDetailView extends MvpView {

    @StateStrategyType(AddToEndSingleStrategy.class)
    void setProgressIndicator(boolean refreshing);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void setNote(Note note);

    @StateStrategyType(SkipStrategy.class)
    void showToast(String message);

    @StateStrategyType(SkipStrategy.class)
    void backPressed();
}
