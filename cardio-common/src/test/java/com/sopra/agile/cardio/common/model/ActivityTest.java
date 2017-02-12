package com.sopra.agile.cardio.common.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class ActivityTest {

    @Test
    public void testDefaultConstructor() {
        Activity activity = new Activity();
        assertObject(activity, null, null, null, null, null);
    }

    @Test
    public void testConstructor() {
        Activity activity = new Activity("id", ActivityType.US, "name", "description", ActivityStatus.DRAFT);
        assertObject(activity, "id", ActivityType.US, "name", "description", ActivityStatus.DRAFT);
    }

    @Test
    public void testFields() {
        Activity activity = new Activity();
        activity.setType(ActivityType.US);
        activity.setId("id");
        activity.setName("name");
        activity.setDescription("description");
        activity.setStatus(ActivityStatus.DRAFT);
        assertObject(activity, "id", ActivityType.US, "name", "description", ActivityStatus.DRAFT);
    }

    private void assertObject(Activity activity, final String id, ActivityType type, final String name,
            final String description, ActivityStatus status) {
        if (type == null) {
            assertNull(activity.getType());
        } else {
            assertEquals(type, activity.getType());
        }
        if (id == null) {
            assertNull(activity.getId());
        } else {
            assertEquals(id, activity.getId());
        }
        if (name == null) {
            assertNull(activity.getName());
        } else {
            assertEquals(name, activity.getName());
        }
        if (description == null) {
            assertNull(activity.getDescription());
        } else {
            assertEquals(description, activity.getDescription());
        }
        if (status == null) {
            assertNull(activity.getStatus());
        } else {
            assertEquals(status, activity.getStatus());
        }
    }

}
