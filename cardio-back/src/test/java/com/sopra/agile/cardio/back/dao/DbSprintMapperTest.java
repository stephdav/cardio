package com.sopra.agile.cardio.back.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Calendar;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

import com.sopra.agile.cardio.common.model.DbSprint;

public class DbSprintMapperTest {

    private static final String SHOULD_BE_EQUALS = "Should be equals";
    private static final String[] STRING_COLUMNS = { DbSprintMapper.COL_ID, DbSprintMapper.COL_NAME,
            DbSprintMapper.COL_GOAL };
    private static final String[] DATE_COLUMNS = { DbSprintMapper.COL_STARTDATE, DbSprintMapper.COL_ENDDATE };

    private static final long NOW = System.currentTimeMillis();

    private DbSprintMapper mapper;
    private ResultSet rs;

    @Before
    public void init() throws SQLException {
        mapper = new DbSprintMapper();
        rs = mock(ResultSet.class);
        for (String col : Arrays.asList(STRING_COLUMNS)) {
            when(rs.getString(col)).thenReturn(col);
        }
        Date today = getDateFromLong(NOW);
        for (String col : Arrays.asList(DATE_COLUMNS)) {
            when(rs.getDate(col)).thenReturn(today);
        }
    }

    @Test
    public void testMapRow() throws SQLException {

        LocalDate today = getLocalDateFromLong(NOW);

        DbSprint usr = mapper.mapRow(rs, 0);
        assertEquals(SHOULD_BE_EQUALS, DbSprintMapper.COL_ID, usr.getId());
        assertEquals(SHOULD_BE_EQUALS, DbSprintMapper.COL_NAME, usr.getName());
        assertEquals(SHOULD_BE_EQUALS, today, usr.getStartDate());
        assertEquals(SHOULD_BE_EQUALS, today, usr.getEndDate());
        assertEquals(SHOULD_BE_EQUALS, DbSprintMapper.COL_GOAL, usr.getGoal());
    }

    @Test
    public void testConvertToLocalDate() {
        assertEquals(SHOULD_BE_EQUALS, getLocalDateFromLong(NOW),
                DbSprintMapper.convertToLocalDate(getDateFromLong(NOW)));
        assertNull(DbSprintMapper.convertToLocalDate(null));
    }

    @Test
    public void convertToDate() {
        assertEquals(SHOULD_BE_EQUALS, getExactDateFromLong(NOW),
                DbSprintMapper.convertToDate(getLocalDateFromLong(NOW)));
        assertNull(DbSprintMapper.convertToLocalDate(null));
    }

    private Date getDateFromLong(long time) {
        Date today = new Date(time);
        return today;
    }

    private Date getExactDateFromLong(long time) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return new Date(cal.getTimeInMillis());
    }

    private LocalDate getLocalDateFromLong(long time) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time);
        return new LocalDate(
                String.format("%s-%s-%s", cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DATE)));
    }

}
