package com.gnoemes.bubblenotes.repo.model;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToMany;
import io.objectbox.relation.ToOne;

/**
 * Created by kenji1947 on 28.09.2017.
 */
@Entity
public class Note {
    @Id
    long id;
    private String name;
    private int priority;

    ToOne<Description> description;
    ToMany<Comment> comments;

    public ToOne<Description> getDescription() {
        return description;
    }

    public ToMany<Comment> getComments() {
        return comments;
    }

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