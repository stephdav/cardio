package com.sopra.agile.cardio.common.model;

import org.joda.time.LocalDate;

public class DbSprint extends Base {

    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private String goal;

    public DbSprint() {
        super();
    }

    public DbSprint(final String id, final String name, final LocalDate startDate, final LocalDate endDate) {
        super(id);
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
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
