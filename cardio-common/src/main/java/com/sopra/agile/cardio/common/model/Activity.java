package com.sopra.agile.cardio.common.model;

public class Activity extends Base {

    private ActivityType type;
    private String name;
    private String description;
    private ActivityStatus status;

    public Activity() {
        super();
    }

    public Activity(final String id, ActivityType type, final String name, final String description,
            ActivityStatus status) {
        super(id);
        this.type = type;
        this.name = name;
        this.description = description;
        this.status = status;
    }

    public ActivityType getType() {
        return type;
    }

    public void setType(ActivityType type) {
        this.type = type;
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
