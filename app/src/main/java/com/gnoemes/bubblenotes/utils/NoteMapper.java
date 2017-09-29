package com.gnoemes.bubblenotes.utils;

import com.gnoemes.bubblenotes.data.model.Note;

public final class NoteMapper {

    public static Note createNoteFromData(String id,String name, int priority) {
        Note note = new Note();
        note.setId(id);
        note.setName(name);
        note.setPriority(priority);
        return note;
    }
}
