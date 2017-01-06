package com.sopra.agile.cardio.back.utils.converter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

import com.sopra.agile.cardio.back.model.DbSprint;
import com.sopra.agile.cardio.common.exception.CardioTechnicalException;
import com.sopra.agile.cardio.common.model.Sprint;

public class ConverterTest {

    // class to test
    private Converter converter;

    @Before
    public void setUp() {
        converter = new Converter();
    }

    @Test
    public void testConvertFromSprintToDbSprint() throws CardioTechnicalException {
        Sprint input = new Sprint("id", null, "2016-07-14", null);
        DbSprint output = converter.map(input);
        assertNotNull(output);
        assertEquals("id", output.getId());
        assertNull(output.getName());
        assertEquals(new LocalDate(2016, 07, 14), output.getStartDate());
        assertNull(output.getEndDate());
    }

    @Test
    public void testConvertFromDbSprintToSprint() throws CardioTechnicalException {
        DbSprint input = new DbSprint("id", null, new LocalDate(2016, 07, 14), null);
        Sprint output = converter.map(input);
        assertNotNull(output);
        assertEquals("id", output.getId());
        assertNull(output.getName());
        assertEquals("2016-07-14", output.getStartDate());
        assertNull(output.getEndDate());
    }

}
