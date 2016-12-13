package com.sopra.agile.cardio.common.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class SprintTest {

    @Test
    public void testDefaultConstructor() {
        Sprint usr = new Sprint();
        assertObject(usr, null, null, null, null);
    }

    @Test
    public void testConstructor() {
        Sprint usr = new Sprint("id", "name", "start", "end");
        assertObject(usr, "id", "name", "start", "end");
    }

    @Test
    public void testFields() {
        Sprint usr = new Sprint();
        usr.setId("id");
        usr.setName("name");
        usr.setStartDate("start");
        usr.setEndDate("end");
        assertObject(usr, "id", "name", "start", "end");
    }

    private void assertObject(Sprint usr, final String id, final String name, final String startDate,
            final String endDate) {
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
