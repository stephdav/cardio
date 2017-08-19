package com.sopra.agile.cardio.common.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class StoryTest {

    @Test
    public void testDefaultConstructor() {
        Story story = new Story();
        assertObject(story, 0, null, null, 0, 0, 0, 0);
    }

    @Test
    public void testConstructor() {
        Story story = new Story(1, "description", StoryStatus.DONE, 2, 3);
        assertObject(story, 1, "description", StoryStatus.DONE, 2, 3, 0, 0);
    }

    @Test
    public void testFields() {
        Story story = new Story();
        story.setId(10);
        story.setDescription("description");
        story.setStatus(StoryStatus.PENDING);
        story.setContribution(20);
        story.setEstimate(30);
        story.setSprint(1);
        story.setAssignedUser(2);
        assertObject(story, 10, "description", StoryStatus.PENDING, 20, 30, 1, 2);
    }

    private void assertObject(Story story, long id, final String description, StoryStatus status, int contribution,
            int estimate, long sprint, long user) {
        assertEquals(id, story.getId());
        if (description == null) {
            assertNull(story.getDescription());
        } else {
            assertEquals(description, story.getDescription());
        }
        if (status == null) {
            assertNull(story.getStatus());
        } else {
            assertEquals(status, story.getStatus());
        }
        assertEquals(contribution, story.getContribution());
        assertEquals(estimate, story.getEstimate());
        assertEquals(sprint, story.getSprint());
        assertEquals(user, story.getAssignedUser());
    }

}