package com.sopra.agile.cardio.common.model;

import java.util.ArrayList;
import java.util.List;

public class Kanban {

    private List<Story> todo;
    private List<Story> pending;
    private List<Story> done;

    public Kanban() {
        // Empty constructor
        todo = new ArrayList<Story>();
        pending = new ArrayList<Story>();
        done = new ArrayList<Story>();
    }

    public List<Story> getTodo() {
        if (todo == null) {
            todo = new ArrayList<Story>();
        }
        return todo;
    }

    public void setTodo(List<Story> todo) {
        this.todo = todo;
    }

    public List<Story> getPending() {
        if (pending == null) {
            pending = new ArrayList<Story>();
        }
        return pending;
    }

    public void setPending(List<Story> pending) {
        this.pending = pending;
    }

    public List<Story> getDone() {
        if (done == null) {
            done = new ArrayList<Story>();
        }
        return done;
    }

    public void setDone(List<Story> done) {
        this.done = done;
    }

}
