package com.sopra.agile.cardio.common.exception;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

public class CardioFunctionalExceptionTest {

    @Test
    public void testConstructorWithMessage() {
        CardioFunctionalException cfe = new CardioFunctionalException("message");
        assertEquals("message", cfe.getMessage());
    }

    @Test
    public void testConstructorWothException() {
        IOException ex = new IOException("io message");
        CardioFunctionalException cfe = new CardioFunctionalException("message", ex);
        assertEquals("message", cfe.getMessage());
    }

}
