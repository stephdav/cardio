package com.sopra.agile.cardio.back.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sopra.agile.cardio.common.exception.CardioFunctionalException;

public class Fields {

    private static final Logger LOGGER = LoggerFactory.getLogger(Fields.class);

    private Fields() {
        // static class
    }

    public static void isMandatory(String fieldname, String field) throws CardioFunctionalException {
        if (field == null || field.trim().isEmpty()) {
            LOGGER.error(fieldname + " is mandatory");
            throw new CardioFunctionalException(fieldname + " is mandatory");
        }
    }

    public static void checkLength(String fieldname, String field, int min, int max) throws CardioFunctionalException {
        if (field != null) {
            int len = field.trim().length();
            if (len < min) {
                LOGGER.error(fieldname + " must have " + min + " characters at least");
                throw new CardioFunctionalException(fieldname + " must have " + min + " characters at least");
            }
            if (len > max) {
                LOGGER.error(fieldname + " must be less than " + max + " characters");
                throw new CardioFunctionalException(fieldname + " must be less than " + max + " characters");
            }
        }
    }

    public static void checkField(String fieldname, String field, int min, int max) throws CardioFunctionalException {
        isMandatory(fieldname, field);
        checkLength(fieldname, field, min, max);
    }
}
