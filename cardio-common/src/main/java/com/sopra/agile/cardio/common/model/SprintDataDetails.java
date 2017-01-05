package com.sopra.agile.cardio.common.model;

import java.util.ArrayList;
import java.util.List;

public class SprintDataDetails {

    private int leftDays;
    private List<String> days;
    private List<Integer> done;
    private List<Integer> ideal;
    private List<Integer> left;

    public SprintDataDetails() {
        // Empty constructor
    }

    public int getLeftDays() {
        return leftDays;
    }

    public void setLeftDays(int leftDays) {
        this.leftDays = leftDays;
    }

    public List<String> getDays() {
        if (days == null) {
            days = new ArrayList<String>();
        }
        return days;
    }

    public void setDays(List<String> days) {
        this.days = days;
    }

    public List<Integer> getDone() {
        if (done == null) {
            done = new ArrayList<Integer>();
        }
        return done;
    }

    public void setDone(List<Integer> done) {
        this.done = done;
    }

    public List<Integer> getIdeal() {
        if (ideal == null) {
            ideal = new ArrayList<Integer>();
        }
        return ideal;
    }

    public void setIdeal(List<Integer> ideal) {
        this.ideal = ideal;
    }

    public List<Integer> getLeft() {
        if (left == null) {
            left = new ArrayList<Integer>();
        }
        return left;
    }

    public void setLeft(List<Integer> left) {
        this.left = left;
    }
}
