package com.sopra.agile.cardio.common.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class SprintDataDetailsTest {

    @Test
    public void testDefaultConstructor() {
        SprintDataDetails details = new SprintDataDetails();
        assertObject(details, 0, new ArrayList<String>(), new ArrayList<Integer>(), new ArrayList<Integer>(),
                new ArrayList<Integer>());
    }

    @Test
    public void testFields() {
        List<String> days = Arrays.asList("A", "B");
        List<Integer> done = Arrays.asList(1, 2);
        List<Integer> ideal = Arrays.asList(1, 2);
        List<Integer> left = Arrays.asList(1, 2);

        SprintDataDetails details = new SprintDataDetails();
        details.setLeftDays(5);
        details.setDays(days);
        details.setDone(done);
        details.setIdeal(ideal);
        details.setLeft(left);

        assertObject(details, 5, days, done, ideal, left);
    }

    public static void assertObject(SprintDataDetails details, int leftDays, final List<String> days,
            final List<Integer> done, final List<Integer> ideal, final List<Integer> left) {
        assertEquals(leftDays, details.getLeftDays());
        if (days == null) {
            assertNull(details.getDays());
        } else {
            assertNotNull(details.getDays());
            for (String day : days) {
                assertTrue(details.getDays().contains(day));
            }
        }
        if (done == null) {
            assertNull(details.getDone());
        } else {
            assertNotNull(details.getDone());
            for (Integer i : done) {
                assertTrue(details.getDone().contains(i));
            }
        }
        if (ideal == null) {
            assertNull(details.getIdeal());
        } else {
            assertNotNull(details.getIdeal());
            for (Integer i : ideal) {
                assertTrue(details.getIdeal().contains(i));
            }
        }
        if (left == null) {
            assertNull(details.getLeft());
        } else {
            assertNotNull(details.getLeft());
            for (Integer i : left) {
                assertTrue(details.getLeft().contains(i));
            }
        }
    }
}
