package com.sopra.agile.cardio.back.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.sopra.agile.cardio.back.model.DbSprint;
import com.sopra.agile.cardio.common.utils.LocalDateUtils;

public class DbSprintMapper implements RowMapper<DbSprint> {

    public static final String COL_ID = "ID";
    public static final String COL_NAME = "NAME";
    public static final String COL_STARTDATE = "START_DATE";
    public static final String COL_ENDDATE = "END_DATE";
    public static final String COL_GOAL = "GOAL";
    public static final String COL_COMMIT = "COMMITMENT";

    public DbSprint mapRow(ResultSet rs, int rowNum) throws SQLException {
        DbSprint sprint = new DbSprint();
        sprint.setId(rs.getString(COL_ID));
        sprint.setName(rs.getString(COL_NAME));
        sprint.setStartDate(LocalDateUtils.convertToLocalDate(rs.getDate(COL_STARTDATE)));
        sprint.setEndDate(LocalDateUtils.convertToLocalDate(rs.getDate(COL_ENDDATE)));
        sprint.setGoal(rs.getString(COL_GOAL));
        sprint.setCommitment(rs.getInt(COL_COMMIT));
        return sprint;
    }

}