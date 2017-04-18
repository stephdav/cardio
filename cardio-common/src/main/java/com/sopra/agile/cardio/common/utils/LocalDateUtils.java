package com.sopra.agile.cardio.common.utils;

import java.sql.Date;
import java.sql.Timestamp;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

public class LocalDateUtils {

    private LocalDateUtils() {
        // static class
    }

    // === LocalDate =========================================================

    public static LocalDate convertToLocalDate(Date date) {
        LocalDate d = null;
        if (date != null) {
            d = new LocalDate(date);
        }
        return d;
    }

    public static Date convertToDate(LocalDate date) {
        Date d = null;
        if (date != null) {
            d = new Date(date.toDate().getTime());
        }
        return d;
    }

    // === LocalDateTime =====================================================

    public static LocalDateTime convertToLocalDateTime(Date date) {
        LocalDateTime d = null;
        if (date != null) {
            d = new LocalDateTime(date);
        }
        return d;
    }

    public static Date convertToDate(LocalDateTime date) {
        Date d = null;
        if (date != null) {
            d = new Date(date.toDate().getTime());
        }
        return d;
    }

    public static LocalDateTime convertToLocalDateTime(Timestamp date) {
        LocalDateTime d = null;
        if (date != null) {
            d = new LocalDateTime(date);
        }
        return d;
    }
}
