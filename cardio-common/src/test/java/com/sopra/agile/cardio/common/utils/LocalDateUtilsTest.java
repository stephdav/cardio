package com.sopra.agile.cardio.common.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.sql.Date;
import java.util.Calendar;

import org.joda.time.LocalDate;
import org.junit.Test;

public class LocalDateUtilsTest {

    private static final long NOW = System.currentTimeMillis();

    private static final String SHOULD_BE_EQUALS = "Should be equals";

    @Test
    public void testConvertToLocalDate() {
        assertEquals(SHOULD_BE_EQUALS, getLocalDateFromLong(NOW),
                LocalDateUtils.convertToLocalDate(getDateFromLong(NOW)));
        assertNull(LocalDateUtils.convertToLocalDate(null));
    }

    @Test
    public void convertToDate() {
        assertEquals(SHOULD_BE_EQUALS, getExactDateFromLong(NOW),
                LocalDateUtils.convertToDate(getLocalDateFromLong(NOW)));
        assertNull(LocalDateUtils.convertToDate(null));
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
