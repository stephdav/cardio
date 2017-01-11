package com.sopra.agile.cardio.common.exception;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

public class CardioTechnicalExceptionTest {

    @Test
    public void testConstructorWithMessage() {
        CardioTechnicalException cfe = new CardioTechnicalException("message");
        assertEquals("message", cfe.getMessage());
    }

    @Test
    public void testConstructorWothException() {
        IOException ex = new IOException("io message");
        CardioTechnicalException cfe = new CardioTechnicalException("message", ex);
        assertEquals("message", cfe.getMessage());
    }

}
