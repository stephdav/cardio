package com.sopra.agile.cardio.common.model;

import java.util.HashMap;
import java.util.Map;

public class SprintData {

    private Map<String, String> data;

    public SprintData() {
        // Empty constructor
    }

    public Map<String, String> getData() {
        if (data == null) {
            return new HashMap<String, String>();
        }
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }

}
