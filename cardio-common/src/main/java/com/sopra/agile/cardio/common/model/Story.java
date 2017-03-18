package com.sopra.agile.cardio.common.model;

import org.joda.time.LocalDateTime;

public class Story extends BaseLong {

    private String description;
    private StoryStatus status;
    private LocalDateTime lastUpdate;
    private int contribution;
    private int estimate;
    private Long assignedUser;

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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((assignedUser == null) ? 0 : assignedUser.hashCode());
        result = prime * result + contribution;
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + estimate;
        result = prime * result + ((lastUpdate == null) ? 0 : lastUpdate.hashCode());
        result = prime * result + ((status == null) ? 0 : status.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Story other = (Story) obj;
        if (assignedUser == null) {
            if (other.assignedUser != null) {
                return false;
            }
        } else if (!assignedUser.equals(other.assignedUser)) {
            return false;
        }
        if (contribution != other.contribution) {
            return false;
        }
        if (description == null) {
            if (other.description != null) {
                return false;
            }
        } else if (!description.equals(other.description)) {
            return false;
        }
        if (estimate != other.estimate) {
            return false;
        }
        if (lastUpdate == null) {
            if (other.lastUpdate != null) {
                return false;
            }
        } else if (!lastUpdate.equals(other.lastUpdate)) {
            return false;
        }
        if (status != other.status) {
            return false;
        }
        return true;
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

    public Long getAssignedUser() {
        return assignedUser;
    }

    public void setAssignedUser(Long assignedUser) {
        this.assignedUser = assignedUser;
    }

}
