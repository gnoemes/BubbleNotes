package com.gnoemes.bubblenotes.data.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.gnoemes.bubblenotes.data.source.local.Config;


/**
 * Created by kenji1947 on 25.09.2017.
 */

@Entity(tableName = Config.TABLE_NAME)
public class Note {

    public static final String ID = "id";
    public static final String SORT_PRIORITY = "priority";

    @PrimaryKey(autoGenerate = true)
    private long id;

    private String name;

    private int priority;

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
}
