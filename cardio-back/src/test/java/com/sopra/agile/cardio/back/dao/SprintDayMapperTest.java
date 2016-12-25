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

import com.sopra.agile.cardio.common.model.SprintDay;

public class SprintDayMapperTest {

	private static final String[] DATE_COLUMNS = { SprintDayMapper.COL_DAY };
	private static final String[] INT_COLUMNS = { SprintDayMapper.COL_DONE };

	private static final long NOW = System.currentTimeMillis();
	private static final int INT_VALUE = 0;

	private static final String SHOULD_BE_EQUALS = "Should be equals";

	private SprintDayMapper mapper;
	private ResultSet rs;

	@Before
	public void init() throws SQLException {
		mapper = new SprintDayMapper();
		rs = mock(ResultSet.class);
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

		SprintDay sprint = mapper.mapRow(rs, 0);
		assertEquals(SHOULD_BE_EQUALS, today, sprint.getDay());
		assertEquals(SHOULD_BE_EQUALS, INT_VALUE, sprint.getDone());
	}

	private Date getDateFromLong(long time) {
		Date today = new Date(time);
		return today;
	}

	private LocalDate getLocalDateFromLong(long time) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(time);
		return new LocalDate(String.format("%s-%s-%s", cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1,
				cal.get(Calendar.DATE)));
	}

}
