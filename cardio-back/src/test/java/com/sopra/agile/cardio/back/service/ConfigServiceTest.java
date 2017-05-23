package com.sopra.agile.cardio.back.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;
import org.springframework.core.env.Environment;
import org.springframework.test.util.ReflectionTestUtils;

public class ConfigServiceTest {

    private ConfigService svc;
    private Environment env;

    private static final String STRING = "KEY";
    private static final String EMPTY = "";
    private static final String INT = "7";
    private static final String DOUBLE = "3.14";
    private static final String UNKNOWN = "UNK.KEY";
    private static final String TRUE = "True";
    private static final String FALSE = "False";

    private static final String STRING_VALUE = "VALUE";
    private static final String EMPTY_VALUE = "";
    private static int INT_VALUE = 7;
    private static double DOUBLE_VALUE = 3.14;

    @Before
    public void init() throws SQLException {
        env = mock(Environment.class);
        svc = new ConfigServiceImpl();

        ReflectionTestUtils.setField(svc, "env", env);
        when(env.getRequiredProperty(EMPTY)).thenReturn(EMPTY_VALUE);
        when(env.getRequiredProperty(STRING)).thenReturn(STRING_VALUE);
        when(env.getRequiredProperty(INT)).thenReturn(String.valueOf(INT_VALUE));
        when(env.getRequiredProperty(DOUBLE)).thenReturn(String.valueOf(DOUBLE_VALUE));
        when(env.getRequiredProperty(UNKNOWN)).thenThrow(new IllegalStateException());
        when(env.getRequiredProperty(TRUE)).thenReturn(String.valueOf(true));
        when(env.getRequiredProperty(FALSE)).thenReturn(String.valueOf(false));
    }

    @Test
    public void testGetProperty() {
        assertEquals(STRING_VALUE, svc.getProperty(STRING));
        assertEquals(EMPTY_VALUE, svc.getProperty(EMPTY));
        assertEquals(INT, svc.getProperty(INT));
        assertEquals(DOUBLE, svc.getProperty(DOUBLE));
        assertEquals(EMPTY_VALUE, svc.getProperty(UNKNOWN));
    }

    @Test
    public void testGetIntProperty() {
        assertEquals(0, svc.getIntProperty(STRING));
        assertEquals(0, svc.getIntProperty(EMPTY));
        assertEquals(INT_VALUE, svc.getIntProperty(INT));
        assertEquals(0, svc.getIntProperty(DOUBLE));
        assertEquals(0, svc.getIntProperty(UNKNOWN));
    }

    @Test
    public void testGetBooleanProperty() {
        assertFalse(svc.getBooleanProperty(null));
        assertFalse(svc.getBooleanProperty(EMPTY));
        assertFalse(svc.getBooleanProperty(STRING));
        assertFalse(svc.getBooleanProperty(INT));
        assertFalse(svc.getBooleanProperty(DOUBLE));
        assertFalse(svc.getBooleanProperty(UNKNOWN));
        assertFalse(svc.getBooleanProperty(FALSE));
        assertTrue(svc.getBooleanProperty(TRUE));
    }
}
