package com.gnoemes.bubblenotes.utils;

import com.gnoemes.bubblenotes.data.note.model.Note;

public final class NoteMapper {

    public static Note createNoteFromData(String name, String description, int priority, String date,boolean complete) {
        Note note = new Note();
        note.setName(name);
        note.setDescription(description);
        note.setDate(date);
        note.setPriority(priority);
        note.setComplete(complete);
        return note;
    }

    public static Note createNoteFromDataWithId(long id,String name,String description, int priority, String date, boolean complete) {
        Note note = new Note();
        note.setId(id);
        note.setName(name);
        note.setDate(date);
        note.setDescription(description);
        note.setPriority(priority);
        note.setComplete(complete);
        return note;
    }
}
