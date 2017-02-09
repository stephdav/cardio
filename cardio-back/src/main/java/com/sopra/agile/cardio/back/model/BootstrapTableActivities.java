package com.sopra.agile.cardio.back.model;

import java.util.List;

import com.sopra.agile.cardio.common.model.Activity;

public class BootstrapTableActivities {

    private int total;
    private List<Activity> rows;

    public BootstrapTableActivities() {
        // Empty constructor
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<Activity> getRows() {
        return rows;
    }

    public void setRows(List<Activity> rows) {
        this.rows = rows;
    }

}
