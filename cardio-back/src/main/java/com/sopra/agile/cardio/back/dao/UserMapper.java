package com.sopra.agile.cardio.back.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.sopra.agile.cardio.common.model.User;

public class UserMapper implements RowMapper<User> {

    public static final String COL_ID = "ID";
    public static final String COL_LOGIN = "LOGIN";
    public static final String COL_FIRSTNAME = "FIRSTNAME";
    public static final String COL_LASTNAME = "LASTNAME";

    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User usr = new User();
        usr.setId(rs.getLong(COL_ID));
        usr.setLogin(rs.getString(COL_LOGIN));
        usr.setFirstname(rs.getString(COL_FIRSTNAME));
        usr.setLastname(rs.getString(COL_LASTNAME));
        return usr;
    }
}