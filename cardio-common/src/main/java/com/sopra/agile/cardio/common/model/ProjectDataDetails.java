package com.sopra.agile.cardio.common.model;

import java.util.ArrayList;
import java.util.List;

public class ProjectDataDetails {

    private int worst;
    private int average;
    private int best;
    private int overCommit;

    private List<String> sprints;
    private List<String> sprintsSample;
    private List<Integer> done;
    private List<Integer> burnup;

    public ProjectDataDetails() {
        // Empty constructor
    }

    public int getWorst() {
        return worst;
    }

    public void setWorst(int worst) {
        this.worst = worst;
    }

    public int getAverage() {
        return average;
    }

    public void setAverage(int average) {
        this.average = average;
    }

    public int getBest() {
        return best;
    }

    public void setBest(int best) {
        this.best = best;
    }

    public int getOverCommit() {
        return overCommit;
    }

    public void setOverCommit(int overCommit) {
        this.overCommit = overCommit;
    }

    public List<String> getSprints() {
        if (sprints == null) {
            sprints = new ArrayList<String>();
        }
        return sprints;
    }

    public void setSprints(List<String> sprints) {
        this.sprints = sprints;
    }

    public List<String> getSprintsSample() {
        if (sprintsSample == null) {
            sprintsSample = new ArrayList<String>();
        }
        return sprintsSample;
    }

    public void setSprintsSample(List<String> sprintsSample) {
        this.sprintsSample = sprintsSample;
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

    public List<Integer> getBurnup() {
        if (burnup == null) {
            burnup = new ArrayList<Integer>();
        }
        return burnup;
    }

    public void setBurnup(List<Integer> burnup) {
        this.burnup = burnup;
    }
}
