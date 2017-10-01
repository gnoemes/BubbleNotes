package com.gnoemes.bubblenotes.repo.model;

import io.objectbox.annotation.Backlink;
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
    private long unixTime;
    boolean complete;

    ToOne<Description> description;
    ToMany<Comment> comments;


    public ToOne<Description> getDescription() {
        return description;
    }

    public void setDescription(ToOne<Description> description) {
        this.description = description;
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

    public long getUnixTime() {
        return unixTime;
    }

    public void setUnixTime(long unixTime) {
        this.unixTime = unixTime;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }
}
