package com.sopra.agile.cardio.common.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class BaseTest {

    @Test
    public void testDefaultConstructor() {
        Base b = new Base();
        assertObject(b, null);
    }

    @Test
    public void testConstructor() {
        Base b = new Base("id");
        assertObject(b, "id");
    }

    @Test
    public void testFields() {
        Base b = new Base();
        b.setId("id");
        assertObject(b, "id");
    }

    private void assertObject(Base b, final String id) {
        if (id == null) {
            assertNull(b.getId());
        } else {
            assertEquals(id, b.getId());
        }
    }

}
