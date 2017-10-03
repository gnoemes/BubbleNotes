package com.gnoemes.bubblenotes.repo.local;

import com.gnoemes.bubblenotes.repo.model.Note;

import java.util.List;

/**
 * Created by kenji1947 on 03.10.2017.
 */

public class Result {
    public Result(List<Note> noteList, boolean description, boolean comments) {
        this.noteList = noteList;
        Description = description;
        Comments = comments;
    }

    List<Note> noteList;
    boolean Description;
    boolean Comments;
}
