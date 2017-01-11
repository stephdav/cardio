package com.sopra.agile.cardio.common.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class ProjectDataDetailsTest {

    @Test
    public void testDefaultConstructor() {
        ProjectDataDetails details = new ProjectDataDetails();
        assertObject(details, new ArrayList<String>(), new ArrayList<String>(), new ArrayList<Integer>(),
                new ArrayList<Integer>(), 0, 0, 0, 0);
    }

    @Test
    public void testFields() {
        List<String> days = Arrays.asList("A", "B", "C");
        List<String> daysSample = Arrays.asList("A", "B");
        List<Integer> done = Arrays.asList(1, 2);
        List<Integer> burnup = Arrays.asList(3, 4);

        ProjectDataDetails details = new ProjectDataDetails();
        details.setSprints(days);
        details.setSprintsSample(daysSample);
        details.setDone(done);
        details.setBurnup(burnup);
        details.setWorst(10);
        details.setAverage(15);
        details.setBest(20);
        details.setOverCommit(7);
        assertObject(details, days, daysSample, done, burnup, 10, 15, 20, 7);
    }

    public static void assertObject(ProjectDataDetails details, final List<String> sprints,
            final List<String> sprintsSample, final List<Integer> done, final List<Integer> burnup, int worst,
            int average, int best, int over) {

        assertNotNull(details.getSprints());
        if (sprints != null) {
            for (String day : sprints) {
                assertTrue(details.getSprints().contains(day));
            }
        }
        assertNotNull(details.getSprintsSample());
        if (sprintsSample != null) {
            for (String day : sprintsSample) {
                assertTrue(details.getSprintsSample().contains(day));
            }
        }
        assertNotNull(details.getDone());
        if (done != null) {
            for (Integer i : done) {
                assertTrue(details.getDone().contains(i));
            }
        }
        assertNotNull(details.getBurnup());
        if (burnup != null) {
            for (Integer i : burnup) {
                assertTrue(details.getBurnup().contains(i));
            }
        }
        assertEquals(worst, details.getWorst());
        assertEquals(average, details.getAverage());
        assertEquals(best, details.getBest());
        assertEquals(over, details.getOverCommit());
    }
}
