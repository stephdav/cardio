package com.sopra.agile.cardio.common.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.joda.time.LocalDate;
import org.junit.Test;

public class DbSprintTest {

    @Test
    public void testDefaultConstructor() {
        DbSprint usr = new DbSprint();
        assertObject(usr, null, null, null, null);
    }

    @Test
    public void testConstructor() {
        LocalDate start = new LocalDate();
        LocalDate end = start.plusDays(14);

        DbSprint usr = new DbSprint("id", "name", start, end);
        assertObject(usr, "id", "name", start, end);
    }

    @Test
    public void testFields() {
        LocalDate start = new LocalDate();
        LocalDate end = start.plusDays(14);

        DbSprint usr = new DbSprint();
        usr.setId("id");
        usr.setName("name");
        usr.setStartDate(start);
        usr.setEndDate(end);
        assertObject(usr, "id", "name", start, end);
    }

    private void assertObject(DbSprint usr, final String id, final String name, final LocalDate startDate,
            final LocalDate endDate) {
        if (id == null) {
            assertNull(usr.getId());
        } else {
            assertEquals(id, usr.getId());
        }
        if (name == null) {
            assertNull(usr.getName());
        } else {
            assertEquals(name, usr.getName());
        }
        if (startDate == null) {
            assertNull(usr.getStartDate());
        } else {
            assertEquals(startDate, usr.getStartDate());
        }
        if (endDate == null) {
            assertNull(usr.getEndDate());
        } else {
            assertEquals(endDate, usr.getEndDate());
        }
    }

}
