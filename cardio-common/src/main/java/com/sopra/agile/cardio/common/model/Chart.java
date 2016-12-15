package com.sopra.agile.cardio.common.model;

import java.util.List;

public class Chart {

    private String[] days;
    private List<Serie> series;

    public Chart() {
        // Empty constructor
    }

    public String[] getDays() {
        return days;
    }

    public void setDays(String[] days) {
        this.days = days;
    }

    public List<Serie> getSeries() {
        return series;
    }

    public void setSeries(List<Serie> series) {
        this.series = series;
    }
}
