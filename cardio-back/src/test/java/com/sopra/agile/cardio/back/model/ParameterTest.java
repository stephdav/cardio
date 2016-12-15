package com.sopra.agile.cardio.back.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class ParameterTest {

    @Test
    public void testDefaultConstructor() {
        Parameter param = new Parameter();
        assertObject(param, null, null);
    }

    @Test
    public void testConstructor() {
        Parameter param = new Parameter("key", "value");
        assertObject(param, "key", "value");
    }

    @Test
    public void testFields() {
        Parameter param = new Parameter();
        param.setKey("key");
        param.setValue("value");
        assertObject(param, "key", "value");
    }

    private void assertObject(Parameter param, final String key, final String value) {
        if (key == null) {
            assertNull(param.getKey());
        } else {
            assertEquals(key, param.getKey());
        }
        if (value == null) {
            assertNull(param.getValue());
        } else {
            assertEquals(value, param.getValue());
        }
    }

}
