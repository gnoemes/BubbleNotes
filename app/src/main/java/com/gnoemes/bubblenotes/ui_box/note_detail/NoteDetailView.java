package com.gnoemes.bubblenotes.ui_box.note_detail;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.gnoemes.bubblenotes.repo_box.model.Note;


/**
 * Created by kenji1947 on 28.09.2017.
 */

public interface NoteDetailView extends MvpView{
    @StateStrategyType(SkipStrategy.class)
    void backPressed();
    @StateStrategyType(AddToEndSingleStrategy.class)
    void setProgressIndicator(boolean refreshing);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void setNote(Note note);

    @StateStrategyType(SkipStrategy.class)
    void showToast(String message);
}
