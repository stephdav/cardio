package com.sopra.agile.cardio.back.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.joda.time.LocalDate;
import org.junit.Test;

public class DbSprintTest {

    @Test
    public void testDefaultConstructor() {
        DbSprint sprint = new DbSprint();
        assertObject(sprint, 0, null, null, null, null, 0, 0);
    }

    @Test
    public void testConstructor() {
        LocalDate start = new LocalDate();
        LocalDate end = start.plusDays(14);

        DbSprint sprint = new DbSprint(1, "name", start, end);
        assertObject(sprint, 1, "name", start, end, null, 0, 0);
    }

    @Test
    public void testFields() {
        LocalDate start = new LocalDate();
        LocalDate end = start.plusDays(14);

        DbSprint sprint = new DbSprint();
        sprint.setId(2);
        sprint.setName("name");
        sprint.setStartDate(start);
        sprint.setEndDate(end);
        sprint.setGoal("goal");
        sprint.setCommitment(999);
        sprint.setVelocity(888);
        assertObject(sprint, 2, "name", start, end, "goal", 999, 888);
    }

    private void assertObject(DbSprint sprint, long id, final String name, final LocalDate startDate,
            final LocalDate endDate, final String goal, int commitment, int velocity) {
        assertEquals(id, sprint.getId());
        if (name == null) {
            assertNull(sprint.getName());
        } else {
            assertEquals(name, sprint.getName());
        }
        if (startDate == null) {
            assertNull(sprint.getStartDate());
        } else {
            assertEquals(startDate, sprint.getStartDate());
        }
        if (endDate == null) {
            assertNull(sprint.getEndDate());
        } else {
            assertEquals(endDate, sprint.getEndDate());
        }
        if (goal == null) {
            assertNull(sprint.getGoal());
        } else {
            assertEquals(goal, sprint.getGoal());
        }
        assertEquals(commitment, sprint.getCommitment());
        assertEquals(velocity, sprint.getVelocity());
    }

}
