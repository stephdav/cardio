package com.sopra.agile.cardio.back.dao;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import com.sopra.agile.cardio.common.model.User;

public class UserMapperTest {

    private static final String SHOULD_BE_EQUALS = "Should be equals";
    private static final String[] STRING_COLUMNS = { UserMapper.COL_LOGIN, UserMapper.COL_FIRSTNAME,
            UserMapper.COL_LASTNAME };
    private static final String[] LONG_COLUMNS = { UserMapper.COL_ID };

    private static final long LONG_VALUE = 0;

    private UserMapper mapper;
    private ResultSet rs;

    @Before
    public void init() throws SQLException {
        mapper = new UserMapper();
        rs = mock(ResultSet.class);
        for (String col : Arrays.asList(STRING_COLUMNS)) {
            when(rs.getString(col)).thenReturn(col);
        }
        for (String col : Arrays.asList(LONG_COLUMNS)) {
            when(rs.getLong(col)).thenReturn(LONG_VALUE);
        }
    }

    @Test
    public void testMapRow() throws SQLException {
        User usr = mapper.mapRow(rs, 0);
        assertEquals(SHOULD_BE_EQUALS, LONG_VALUE, usr.getId());
        assertEquals(SHOULD_BE_EQUALS, UserMapper.COL_LOGIN, usr.getLogin());
        assertEquals(SHOULD_BE_EQUALS, UserMapper.COL_FIRSTNAME, usr.getFirstname());
        assertEquals(SHOULD_BE_EQUALS, UserMapper.COL_LASTNAME, usr.getLastname());
    }
}
