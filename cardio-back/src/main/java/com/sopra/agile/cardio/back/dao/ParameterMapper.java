package com.sopra.agile.cardio.back.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.sopra.agile.cardio.common.model.Parameter;

public class ParameterMapper implements RowMapper<Parameter> {
    public Parameter mapRow(ResultSet rs, int rowNum) throws SQLException {
        Parameter param = new Parameter();
        param.setKey(rs.getString(1));
        param.setValue(rs.getString(2));
        return param;
    }
}