package com.sopra.agile.cardio.common.exception;

public class CardioTechnicalException extends Exception {

    /** serialVersionUID */
    private static final long serialVersionUID = 4117148356799786784L;

    public CardioTechnicalException(String message) {
        super(message);
    }

    public CardioTechnicalException(String message, Throwable cause) {
        super(message, cause);
    }
}
