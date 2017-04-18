package com.sopra.agile.cardio.back.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.sopra.agile.cardio.common.model.Story;
import com.sopra.agile.cardio.common.model.StoryStatus;
import com.sopra.agile.cardio.common.utils.LocalDateUtils;

public class StoryMapper implements RowMapper<Story> {

    public static final String COL_ID = "ID";
    public static final String COL_DESC = "DESCRIPTION";
    public static final String COL_STATUS = "STATUS";
    public static final String COL_LASTUPD = "LAST_UPDATE";
    public static final String COL_CONTRIB = "CONTRIBUTION";
    public static final String COL_ESTIMATE = "ESTIMATE";
    public static final String COL_ASSIGNED = "ASSIGNED";

    public Story mapRow(ResultSet rs, int rowNum) throws SQLException {
        Story story = new Story();
        story.setId(rs.getLong(COL_ID));
        story.setDescription(rs.getString(COL_DESC));
        if (rs.getString(COL_STATUS) == null) {
            story.setStatus(StoryStatus.DRAFT);
        } else {
            story.setStatus(StoryStatus.valueOf(rs.getString(COL_STATUS)));
        }
        story.setLastUpdate(LocalDateUtils.convertToLocalDateTime(rs.getTimestamp(COL_LASTUPD)));
        story.setContribution(rs.getInt(COL_CONTRIB));
        story.setEstimate(rs.getInt(COL_ESTIMATE));
        if (rs.getLong(COL_ASSIGNED) != 0) {
            story.setAssignedUser(rs.getLong(COL_ASSIGNED));
        }
        return story;
    }

}