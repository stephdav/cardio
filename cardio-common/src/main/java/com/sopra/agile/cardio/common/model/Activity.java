package com.sopra.agile.cardio.common.model;

public class Activity extends Base {

    private String name;
    private String description;
    private ActivityStatus status;

    public Activity() {
        super();
    }

    public Activity(final String id, final String name, final String description) {
        super(id);
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ActivityStatus getStatus() {
        return status;
    }

    public void setStatus(ActivityStatus status) {
        this.status = status;
    }
}
