package com.sopra.agile.cardio.common.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class SprintTest {

    @Test
    public void testDefaultConstructor() {
        Sprint sprint = new Sprint();
        assertObject(sprint, null, null, null, null, null, 0);
    }

    @Test
    public void testConstructor() {
        Sprint sprint = new Sprint("id", "name", "start", "end");
        assertObject(sprint, "id", "name", "start", "end", null, 0);
    }

    @Test
    public void testFields() {
        Sprint sprint = new Sprint();
        sprint.setId("id");
        sprint.setName("name");
        sprint.setStartDate("start");
        sprint.setEndDate("end");
        sprint.setGoal("goal");
        sprint.setCommitment(666);
        assertObject(sprint, "id", "name", "start", "end", "goal", 666);
    }

    private void assertObject(Sprint sprint, final String id, final String name, final String startDate,
            final String endDate, final String goal, int commitment) {
        if (id == null) {
            assertNull(sprint.getId());
        } else {
            assertEquals(id, sprint.getId());
        }
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
    }

}
