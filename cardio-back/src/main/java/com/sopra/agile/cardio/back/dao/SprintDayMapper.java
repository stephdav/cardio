package com.sopra.agile.cardio.back.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.sopra.agile.cardio.common.model.SprintDay;
import com.sopra.agile.cardio.common.utils.LocalDateUtils;

public class SprintDayMapper implements RowMapper<SprintDay> {

	public static final String COL_DAY = "DAY";
	public static final String COL_DONE = "DONE";

	public SprintDay mapRow(ResultSet rs, int rowNum) throws SQLException {
		SprintDay sprintDay = new SprintDay();
		sprintDay.setDay(LocalDateUtils.convertToLocalDate(rs.getDate(COL_DAY)));
		sprintDay.setDone(rs.getInt(COL_DONE));
		return sprintDay;
	}

}