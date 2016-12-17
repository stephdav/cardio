package com.sopra.agile.cardio.common.utils;

import java.sql.Date;

import org.joda.time.LocalDate;

public class LocalDateUtils {

    private LocalDateUtils() {
        // static class
    }

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
}
