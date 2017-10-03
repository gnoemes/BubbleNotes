package com.gnoemes.bubblenotes.utils;

import com.gnoemes.bubblenotes.data.model.Note;

public final class NoteMapper {

    public static Note createNoteFromData(String name, int priority) {
        Note note = new Note();
        note.setName(name);
        note.setPriority(priority);
        return note;
    }

    public static Note createNoteFromDataWithId(long id,String name, int priority) {
        Note note = new Note();
        note.setId(id);
        note.setName(name);
        note.setPriority(priority);
        return note;
    }
}
