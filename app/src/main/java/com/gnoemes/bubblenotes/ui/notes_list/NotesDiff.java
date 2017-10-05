package com.gnoemes.bubblenotes.ui.notes_list;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import com.gnoemes.bubblenotes.repo.model.Note;

import java.util.List;

/**
 * Created by kenji1947 on 02.10.2017.
 */

public class NotesDiff extends DiffUtil.Callback {
    private List<Note> newList;
    private List<Note> oldList;

    public NotesDiff(List<Note> newList, List<Note> oldList) {
        this.newList = newList;
        this.oldList = oldList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).getId() == newList.get(newItemPosition).getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).equals(newList.get(newItemPosition));
    }

//    @Nullable
//    @Override
//    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
//        return super.getChangePayload(oldItemPosition, newItemPosition);
//    }
}
