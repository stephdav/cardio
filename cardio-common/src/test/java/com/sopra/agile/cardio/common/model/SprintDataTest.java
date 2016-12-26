package com.sopra.agile.cardio.common.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class SprintDataTest {

    @Test
    public void testDefaultConstructor() {
        SprintData sprintData = new SprintData();
        assertObject(sprintData, new HashMap<String, String>());
    }

    @Test
    public void testFields() {
        SprintData sprintData = new SprintData();
        Map<String, String> data = new HashMap<String, String>();
        data.put("key1", "value");
        data.put("key2", null);
        sprintData.setData(data);
        assertObject(sprintData, data);
    }

    private void assertObject(SprintData sprintData, final Map<String, String> data) {
        if (data == null) {
            assertNull(sprintData.getData());
        } else {
            assertNotNull(sprintData.getData());
            for (Map.Entry<String, String> entry : data.entrySet()) {
                assertTrue(sprintData.getData().containsKey(entry.getKey()));
                assertEquals(entry.getValue(), sprintData.getData().get(entry.getKey()));
            }
        }
    }

}
