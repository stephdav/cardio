package com.sopra.agile.cardio.common.model;

public class Sprint extends Base {

    private String name;
    private String startDate;
    private String endDate;

    public Sprint() {
        super();
    }

    public Sprint(final String id, final String name, final String startDate, String endDate) {
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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

}
