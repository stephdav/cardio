package com.sopra.agile.cardio.common.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class SprintTest {

    @Test
    public void testDefaultConstructor() {
        Sprint sprint = new Sprint();
        assertObject(sprint, 0, null, null, null, null, 0, 0);
    }

    @Test
    public void testConstructor() {
        Sprint sprint = new Sprint(1, "name", "start", "end");
        assertObject(sprint, 1, "name", "start", "end", null, 0, 0);
    }

    @Test
    public void testFields() {
        Sprint sprint = new Sprint();
        sprint.setId(2);
        sprint.setName("name");
        sprint.setStartDate("start");
        sprint.setEndDate("end");
        sprint.setGoal("goal");
        sprint.setCommitment(666);
        sprint.setVelocity(555);
        assertObject(sprint, 2, "name", "start", "end", "goal", 666, 555);
    }

    private void assertObject(Sprint sprint, long id, final String name, final String startDate, final String endDate,
            final String goal, int commitment, int velocity) {
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
