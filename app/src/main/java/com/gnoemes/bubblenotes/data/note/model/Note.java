package com.gnoemes.bubblenotes.data.note.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.gnoemes.bubblenotes.data.note.model.room.Config;


/**
 * Created by kenji1947 on 25.09.2017.
 */

@Entity(tableName = Config.TABLE_NAME)
public class Note {

    public static final String ID = "id";
    public static final String SORT_PRIORITY = "priority";
    public static final String SORT_COMPLETE = "complete";

    @PrimaryKey(autoGenerate = true)
    private long id;

    private String name;

    private int priority;

    private String description;

    private String date;

    private boolean complete;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }
}
