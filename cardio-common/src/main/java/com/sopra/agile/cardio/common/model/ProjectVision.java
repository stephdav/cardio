package com.sopra.agile.cardio.common.model;

import java.util.ArrayList;
import java.util.List;

public class ProjectVision {

    private List<String> lines;

    public ProjectVision() {
        // Empty constructor
    }

    public List<String> getLines() {
        if (lines == null) {
            lines = new ArrayList<String>();
        }
        return lines;
    }

    public void setLines(List<String> lines) {
        this.lines = lines;
    }

}
