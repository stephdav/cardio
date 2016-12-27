package com.sopra.agile.cardio.back.dao;

import static org.junit.Assert.assertEquals;
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

import com.sopra.agile.cardio.back.model.DbSprint;

public class DbSprintMapperTest {

    private static final String[] STRING_COLUMNS = { DbSprintMapper.COL_ID, DbSprintMapper.COL_NAME,
            DbSprintMapper.COL_GOAL };
    private static final String[] DATE_COLUMNS = { DbSprintMapper.COL_STARTDATE, DbSprintMapper.COL_ENDDATE };
    private static final String[] INT_COLUMNS = { DbSprintMapper.COL_COMMIT, DbSprintMapper.COL_VELOCITY };

    private static final long NOW = System.currentTimeMillis();
    private static final int INT_VALUE = 0;

    private static final String SHOULD_BE_EQUALS = "Should be equals";

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
        for (String col : Arrays.asList(INT_COLUMNS)) {
            when(rs.getInt(col)).thenReturn(INT_VALUE);
        }
    }

    @Test
    public void testMapRow() throws SQLException {

        LocalDate today = getLocalDateFromLong(NOW);

        DbSprint sprint = mapper.mapRow(rs, 0);
        assertEquals(SHOULD_BE_EQUALS, DbSprintMapper.COL_ID, sprint.getId());
        assertEquals(SHOULD_BE_EQUALS, DbSprintMapper.COL_NAME, sprint.getName());
        assertEquals(SHOULD_BE_EQUALS, today, sprint.getStartDate());
        assertEquals(SHOULD_BE_EQUALS, today, sprint.getEndDate());
        assertEquals(SHOULD_BE_EQUALS, DbSprintMapper.COL_GOAL, sprint.getGoal());
        assertEquals(SHOULD_BE_EQUALS, INT_VALUE, sprint.getCommitment());
        assertEquals(SHOULD_BE_EQUALS, INT_VALUE, sprint.getVelocity());
    }

    private Date getDateFromLong(long time) {
        Date today = new Date(time);
        return today;
    }

    private LocalDate getLocalDateFromLong(long time) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time);
        return new LocalDate(
                String.format("%s-%s-%s", cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DATE)));
    }

}
