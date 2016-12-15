package com.sopra.agile.cardio.back.model;

import org.joda.time.LocalDate;

public class DbSprint {

    private String id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private String goal;

    public DbSprint() {
        // Empty constructor
    }

    public DbSprint(final String id, final String name, final LocalDate startDate, final LocalDate endDate) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
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

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }
}
