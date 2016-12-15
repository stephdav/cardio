package com.sopra.agile.cardio.back.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.joda.time.LocalDate;
import org.junit.Test;

import com.sopra.agile.cardio.back.model.DbSprint;

public class DbSprintTest {

    @Test
    public void testDefaultConstructor() {
        DbSprint usr = new DbSprint();
        assertObject(usr, null, null, null, null, null);
    }

    @Test
    public void testConstructor() {
        LocalDate start = new LocalDate();
        LocalDate end = start.plusDays(14);

        DbSprint usr = new DbSprint("id", "name", start, end);
        assertObject(usr, "id", "name", start, end, null);
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
        usr.setGoal("goal");
        assertObject(usr, "id", "name", start, end, "goal");
    }

    private void assertObject(DbSprint usr, final String id, final String name, final LocalDate startDate,
            final LocalDate endDate, final String goal) {
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
        if (goal == null) {
            assertNull(usr.getGoal());
        } else {
            assertEquals(goal, usr.getGoal());
        }
    }

}
