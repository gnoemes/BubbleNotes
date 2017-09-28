package com.gnoemes.bubblenotes.data.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by kenji1947 on 25.09.2017.
 */


public class Note extends RealmObject {

    public static final String ID = "id";
    public static final String SORT_PRIORITY = "priority";

    @PrimaryKey
    private String id;

    private String name;

    private int priority;

    public Note() {
    }

    public Note(String id, String name) {
        this.id = id;
        this.name = name;
        this.priority = 2;
    }

    public Note(String id, String name, int priority) {
        this.id = id;
        this.name = name;
        this.priority = priority;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
