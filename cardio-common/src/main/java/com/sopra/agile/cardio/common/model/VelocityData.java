package com.sopra.agile.cardio.common.model;

public class VelocityData {

    private int worst;
    private int average;
    private int best;
    private int overCommit;

    private String[] names;
    private Integer[] data;

    public VelocityData() {
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

    public String[] getNames() {
        return names;
    }

    public void setNames(String[] names) {
        this.names = names;
    }

    public Integer[] getData() {
        return data;
    }

    public void setData(Integer[] data) {
        this.data = data;
    }

}
