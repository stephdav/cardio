package com.sopra.agile.cardio.back.model;

import java.util.List;

public class BootstrapTableList<T> {

    private int total;
    private List<T> rows;

    public BootstrapTableList() {
        // Empty constructor
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

}
