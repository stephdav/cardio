package com.sopra.agile.cardio.common.model;

import org.joda.time.LocalDateTime;

public class Story extends BaseLong {

    private String description;
    private StoryStatus status;
    private LocalDateTime lastUpdate;
    private int contribution;
    private int estimate;

    public Story() {
        super();
    }

    public Story(long id, final String description, StoryStatus status, int contribution, int estimate) {
        super(id);
        this.description = description;
        this.status = status;
        this.contribution = contribution;
        this.estimate = estimate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public StoryStatus getStatus() {
        return status;
    }

    public void setStatus(StoryStatus status) {
        this.status = status;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public int getContribution() {
        return contribution;
    }

    public void setContribution(int contribution) {
        this.contribution = contribution;
    }

    public int getEstimate() {
        return estimate;
    }

    public void setEstimate(int estimate) {
        this.estimate = estimate;
    }

}
