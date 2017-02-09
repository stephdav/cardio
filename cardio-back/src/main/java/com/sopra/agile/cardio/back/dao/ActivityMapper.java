package com.sopra.agile.cardio.back.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.sopra.agile.cardio.common.model.Activity;
import com.sopra.agile.cardio.common.model.ActivityStatus;

public class ActivityMapper implements RowMapper<Activity> {

    public static final String COL_ID = "ID";
    public static final String COL_NAME = "NAME";
    public static final String COL_DESC = "DESCRIPTION";
    public static final String COL_STATUS = "STATUS";

    public Activity mapRow(ResultSet rs, int rowNum) throws SQLException {
        Activity activity = new Activity();
        activity.setId(rs.getString(COL_ID));
        activity.setName(rs.getString(COL_NAME));
        activity.setDescription(rs.getString(COL_DESC));
        if (rs.getString(COL_STATUS) == null) {
            activity.setStatus(ActivityStatus.DRAFT);
        } else {
            activity.setStatus(ActivityStatus.valueOf(rs.getString(COL_STATUS)));
        }
        return activity;
    }

}