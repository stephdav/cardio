package com.sopra.agile.cardio.common.exception;

public class CardioFunctionalException extends Exception {

    /** serialVersionUID */
    private static final long serialVersionUID = -1731037360820838385L;

    public CardioFunctionalException(String message) {
        super(message);
    }

    public CardioFunctionalException(String message, Throwable cause) {
        super(message, cause);
    }
}
