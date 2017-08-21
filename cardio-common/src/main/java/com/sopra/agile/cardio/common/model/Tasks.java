package com.sopra.agile.cardio.common.model;

import org.joda.time.LocalDate;

public class Tasks {

    private long id;
    private LocalDate day;
    private boolean spe;
    private boolean alm;
    private boolean qua;
    private int todo;
    private int pending;
    private int done;

    public Tasks() {
        // Empty constructor
    }

    public Tasks(LocalDate day, int commitmentLeft) {
        this.day = day;
        this.done = commitmentLeft;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getDay() {
        return day;
    }

    public void setDay(LocalDate day) {
        this.day = day;
    }

    public boolean isSpe() {
        return spe;
    }

    public void setSpe(boolean spe) {
        this.spe = spe;
    }

    public boolean isAlm() {
        return alm;
    }

    public void setAlm(boolean alm) {
        this.alm = alm;
    }

    public boolean isQua() {
        return qua;
    }

    public void setQua(boolean qua) {
        this.qua = qua;
    }

    public int getTodo() {
        return todo;
    }

    public void setTodo(int todo) {
        this.todo = todo;
    }

    public int getPending() {
        return pending;
    }

    public void setPending(int pending) {
        this.pending = pending;
    }

    public int getDone() {
        return done;
    }

    public void setDone(int done) {
        this.done = done;
    }

}
