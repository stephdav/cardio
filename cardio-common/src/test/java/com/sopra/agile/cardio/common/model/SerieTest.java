package com.sopra.agile.cardio.common.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class SerieTest {

    @Test
    public void testDefaultConstructor() {
        Serie serie = new Serie();
        assertObject(serie, null, null);
    }

    @Test
    public void testConstructor() {
        Serie serie = new Serie("name", new Double[] { 1d, 2d, 3d });
        assertObject(serie, "name", new Double[] { 1d, 2d, 3d });
    }

    @Test
    public void testFields() {
        Serie serie = new Serie();
        serie.setName("name");
        serie.setData(new Double[] { 1d, 2d, 3d });
        assertObject(serie, "name", new Double[] { 1d, 2d, 3d });
    }

    private void assertObject(Serie serie, final String name, final Double[] data) {
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
