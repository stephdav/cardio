package com.sopra.agile.cardio.back.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.sopra.agile.cardio.common.model.Tasks;
import com.sopra.agile.cardio.common.utils.LocalDateUtils;

public class TasksMapper implements RowMapper<Tasks> {

    public static final String COL_DAY = "DAY";
    public static final String COL_TODO = "TODO";
    public static final String COL_PENDING = "PENDING";
    public static final String COL_DONE = "DONE";
    public static final String COL_SPECIF = "SPECIF";
    public static final String COL_ALM = "ALM";
    public static final String COL_QUALIF = "QUALIF";

    public Tasks mapRow(ResultSet rs, int rowNum) throws SQLException {
        Tasks tasks = new Tasks();
        tasks.setDay(LocalDateUtils.convertToLocalDate(rs.getDate(COL_DAY)));
        tasks.setSpe(rs.getBoolean(COL_SPECIF));
        tasks.setAlm(rs.getBoolean(COL_ALM));
        tasks.setQua(rs.getBoolean(COL_QUALIF));
        tasks.setTodo(rs.getInt(COL_TODO));
        tasks.setPending(rs.getInt(COL_PENDING));
        tasks.setDone(rs.getInt(COL_DONE));
        return tasks;
    }

}