package com.sopra.agile.cardio.back.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.sopra.agile.cardio.common.model.StoryMonitor;
import com.sopra.agile.cardio.common.model.StoryStatus;

public class StoryMonitorMapper implements RowMapper<StoryMonitor> {

    public static final String COL_ID = "ID";
    public static final String COL_DESC = "DESCRIPTION";
    public static final String COL_STATUS = "STATUS";
    public static final String COL_CONTRIB = "CONTRIBUTION";
    public static final String COL_ESTIMATE = "ESTIMATE";
    public static final String COL_SPRINT = "SPRINT";
    public static final String COL_ASSIGNED = "LOGIN";

    public StoryMonitor mapRow(ResultSet rs, int rowNum) throws SQLException {
        StoryMonitor story = new StoryMonitor();
        story.setId(rs.getLong(COL_ID));
        story.setDescription(rs.getString(COL_DESC));
        if (rs.getString(COL_STATUS) == null) {
            story.setStatus(StoryStatus.DRAFT);
        } else {
            story.setStatus(StoryStatus.valueOf(rs.getString(COL_STATUS)));
        }
        story.setContribution(rs.getInt(COL_CONTRIB));
        story.setEstimate(rs.getInt(COL_ESTIMATE));
        story.setAssignedUser(rs.getString(COL_ASSIGNED));
        return story;
    }

}