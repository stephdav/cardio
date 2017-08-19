package com.sopra.agile.cardio.back.dao;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import com.sopra.agile.cardio.common.model.Story;
import com.sopra.agile.cardio.common.model.StoryStatus;

public class StoryMapperTest {

    private static final String SHOULD_BE_EQUALS = "Should be equals";
    private static final String[] LONG_COLUMNS = { StoryMapper.COL_ID, StoryMapper.COL_SPRINT,
            StoryMapper.COL_ASSIGNED };
    private static final String[] STRING_COLUMNS = { StoryMapper.COL_DESC };
    private static final String[] INT_COLUMNS = { StoryMapper.COL_CONTRIB, StoryMapper.COL_ESTIMATE };

    private static final int INT_VALUE = 0;
    private static final long LONG_VALUE = 0;

    private StoryMapper mapper;
    private ResultSet rs;

    @Before
    public void init() throws SQLException {
        mapper = new StoryMapper();
        rs = mock(ResultSet.class);
        for (String col : Arrays.asList(INT_COLUMNS)) {
            when(rs.getInt(col)).thenReturn(INT_VALUE);
        }
        for (String col : Arrays.asList(LONG_COLUMNS)) {
            when(rs.getLong(col)).thenReturn(LONG_VALUE);
        }
    }

    @Test
    public void testMapRow() throws SQLException {

        for (String col : Arrays.asList(STRING_COLUMNS)) {
            when(rs.getString(col)).thenReturn(col);
        }
        when(rs.getString(StoryMapper.COL_STATUS)).thenReturn(StoryStatus.PENDING.toString());

        Story usr = mapper.mapRow(rs, 0);
        assertEquals(SHOULD_BE_EQUALS, LONG_VALUE, usr.getId());
        assertEquals(SHOULD_BE_EQUALS, StoryMapper.COL_DESC, usr.getDescription());
        assertEquals(SHOULD_BE_EQUALS, StoryStatus.PENDING.toString(), usr.getStatus().toString());
        assertEquals(SHOULD_BE_EQUALS, INT_VALUE, usr.getContribution());
        assertEquals(SHOULD_BE_EQUALS, INT_VALUE, usr.getEstimate());
        assertEquals(SHOULD_BE_EQUALS, LONG_VALUE, usr.getSprint());
        assertEquals(SHOULD_BE_EQUALS, LONG_VALUE, usr.getAssignedUser());
    }

    @Test
    public void testMapRowWithNulls() throws SQLException {
        for (String col : Arrays.asList(STRING_COLUMNS)) {
            when(rs.getString(col)).thenReturn(null);
        }
        when(rs.getString(StoryMapper.COL_STATUS)).thenReturn(null);

        Story usr = mapper.mapRow(rs, 0);
        assertEquals(SHOULD_BE_EQUALS, null, usr.getDescription());
        assertEquals(SHOULD_BE_EQUALS, StoryStatus.DRAFT.toString(), usr.getStatus().toString());
    }
}
