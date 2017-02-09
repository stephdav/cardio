package com.sopra.agile.cardio.common.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class ActivityTest {

    @Test
    public void testDefaultConstructor() {
        Activity activity = new Activity();
        assertObject(activity, null, null, null);
    }

    @Test
    public void testConstructor() {
        Activity activity = new Activity("id", "name", "description");
        assertObject(activity, "id", "name", "description");
    }

    @Test
    public void testFields() {
        Activity activity = new Activity();
        activity.setId("id");
        activity.setName("name");
        activity.setDescription("description");
        assertObject(activity, "id", "name", "description");
    }

    private void assertObject(Activity activity, final String id, final String name, final String description) {
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
    }

}
