package com.sopra.agile.cardio.common.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class ChartTest {

    @Test
    public void testDefaultConstructor() {
        Chart chart = new Chart();
        assertObject(chart, null, null);
    }

    @Test
    public void testFields() {
        Serie s1 = new Serie("name1", new Double[] { 1d, 2d, 3d });
        Serie s2 = new Serie("name2", new Double[] { 3.14d, 4.56d, 99.9d });

        Chart chart = new Chart();
        chart.setDays(new String[] { "A", "B", "C" });
        chart.setSeries(Arrays.asList(s1, s2));
        assertObject(chart, new String[] { "A", "B", "C" }, Arrays.asList(s1, s2));
    }

    private void assertObject(Chart chart, final String[] days, final List<Serie> series) {
        if (days == null) {
            assertNull(chart.getDays());
        } else {
            assertNotNull(chart.getDays());
            assertEquals(days.length, chart.getDays().length);
            for (int idx = 0; idx < days.length; idx++) {
                assertEquals(days[idx], chart.getDays()[idx]);
            }
        }
        if (series == null) {
            assertNull(chart.getSeries());
        } else {
            assertNotNull(chart.getSeries());
            assertEquals(series.size(), chart.getSeries().size());
            for (int idx = 0; idx < series.size(); idx++) {
                assertSerieObject(chart.getSeries().get(idx), series.get(idx).getName(), series.get(idx).getData());
            }
        }
    }

    private void assertSerieObject(Serie serie, final String name, final Double[] data) {
        if (name == null) {
            assertNull(serie.getName());
        } else {
            assertEquals(name, serie.getName());
        }
        if (data == null) {
            assertNull(serie.getData());
        } else {
            assertNotNull(serie.getData());
            assertEquals(data.length, serie.getData().length);
            for (int idx = 0; idx < data.length; idx++) {
                assertEquals(data[idx], serie.getData()[idx], 0.01d);
            }
        }
    }

}
