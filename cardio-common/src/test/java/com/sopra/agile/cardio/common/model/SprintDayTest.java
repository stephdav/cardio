package com.sopra.agile.cardio.common.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.joda.time.LocalDate;
import org.junit.Test;

import com.sopra.agile.cardio.common.model.SprintDay;

public class SprintDayTest {

    @Test
    public void testDefaultConstructor() {
        SprintDay sprintDay = new SprintDay();
        assertObject(sprintDay, null, 0);
    }

    @Test
    public void testConstructor() {
        LocalDate day = new LocalDate();
        SprintDay sprintDay = new SprintDay(day, 7);
        assertObject(sprintDay, day, 7);
    }

    @Test
    public void testFields() {
        LocalDate day = new LocalDate();

        SprintDay sprintDay = new SprintDay();
        sprintDay.setDay(day);
        sprintDay.setCommitmentLeft(999);
        assertObject(sprintDay, day, 999);
    }

    private void assertObject(SprintDay sprintDay, final LocalDate day, int commitmentLeft) {
        if (day == null) {
            assertNull(sprintDay.getDay());
        } else {
            assertEquals(day, sprintDay.getDay());
        }
        assertEquals(commitmentLeft, sprintDay.getCommitmentLeft());
    }

}
