package com.sopra.agile.cardio.back.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.joda.time.LocalDate;
import org.junit.Test;

public class LocalDateUtilsTest {

    @Test
    public void testGetWorkingDays() {
        assertWorkingDays(
                new String[] { "2016-12-01", "2016-12-02", "2016-12-05", "2016-12-06", "2016-12-07", "2016-12-08" },
                "2016-12-01", "2016-12-08");
        assertWorkingDays(new String[] { "2016-12-01", "2016-12-02", "2016-12-05", "2016-12-06", "2016-12-07",
                "2016-12-08", "2016-12-09" }, "2016-12-01", "2016-12-11");
        assertWorkingDays(new String[] { "2016-12-05", "2016-12-06", "2016-12-07", "2016-12-08" }, "2016-12-03",
                "2016-12-08");
        assertWorkingDays(new String[] { "2016-12-05", "2016-12-06", "2016-12-07", "2016-12-08", "2016-12-09" },
                "2016-12-03", "2016-12-10");

        // TODO: test with from null
    }

    private void assertWorkingDays(String[] expectedDays, String from, String to) {
        List<String> days = LocalDateUtils.getWorkingDaysAsList(new LocalDate(from), new LocalDate(to));
        if (expectedDays == null) {
            assertNull(days);
        } else {
            assertNotNull(days);
            assertEquals(days.size(), expectedDays.length);
            for (String d : expectedDays) {
                assertTrue(days.contains(d));
            }
        }
    }

    @Test
    public void testGetNumberOfWorkingDays() {
        assertNumberOfWorkingDays(6, "2016-12-01", "2016-12-08");
        assertNumberOfWorkingDays(17, "2016-12-01", "2016-12-25");
        assertNumberOfWorkingDays(4, "2016-12-03", "2016-12-08");
        assertNumberOfWorkingDays(15, "2016-12-03", "2016-12-25");
    }

    private void assertNumberOfWorkingDays(int nb, String from, String to) {
        assertEquals(nb, LocalDateUtils.getNumberOfWorkingDays(new LocalDate(from), new LocalDate(to)));
    }

    @Test
    public void testIsWorkingDay() {
        assertTrue(LocalDateUtils.isWorkingDay(new LocalDate("2016-12-05")));
        assertTrue(LocalDateUtils.isWorkingDay(new LocalDate("2016-12-06")));
        assertTrue(LocalDateUtils.isWorkingDay(new LocalDate("2016-12-07")));
        assertTrue(LocalDateUtils.isWorkingDay(new LocalDate("2016-12-08")));
        assertTrue(LocalDateUtils.isWorkingDay(new LocalDate("2016-12-09")));
        assertFalse(LocalDateUtils.isWorkingDay(new LocalDate("2016-12-10")));
        assertFalse(LocalDateUtils.isWorkingDay(new LocalDate("2016-12-11")));

    }

}
