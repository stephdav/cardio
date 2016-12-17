package com.sopra.agile.cardio.common.model;

import org.joda.time.LocalDate;

public class SprintDay {

    private LocalDate day;
    private int commitmentLeft;

    public SprintDay() {
        // Empty constructor
    }

    public SprintDay(LocalDate day, int commitmentLeft) {
        this.day = day;
        this.commitmentLeft = commitmentLeft;
    }

    public LocalDate getDay() {
        return day;
    }

    public void setDay(LocalDate day) {
        this.day = day;
    }

    public int getCommitmentLeft() {
        return commitmentLeft;
    }

    public void setCommitmentLeft(int commitmentLeft) {
        this.commitmentLeft = commitmentLeft;
    }

}
