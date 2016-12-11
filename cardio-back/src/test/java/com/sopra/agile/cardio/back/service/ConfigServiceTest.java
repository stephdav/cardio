package com.sopra.agile.cardio.back.service;

import static org.junit.Assert.assertEquals;
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

    @Before
    public void init() throws SQLException {
        env = mock(Environment.class);
        svc = new ConfigServiceImpl();

        ReflectionTestUtils.setField(svc, "env", env);
        when(env.getRequiredProperty("KEY")).thenReturn("VALUE");
        when(env.getRequiredProperty("UNKNOWN KEY")).thenThrow(new IllegalStateException());
    }

    @Test
    public void testGetProperty() {
        assertEquals("VALUE", svc.getProperty("KEY"));
        assertEquals("", svc.getProperty("UNKNOWN KEY"));
    }

}
