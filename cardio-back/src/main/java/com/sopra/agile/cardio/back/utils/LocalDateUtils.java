package com.sopra.agile.cardio.back.utils;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;

public class LocalDateUtils {

    private LocalDateUtils() {
        // static class
    }

    public static String[] getWorkingDays(LocalDate fromDate, LocalDate toDate) {
        List<String> workingDays = new ArrayList<>();

        LocalDate date = fromDate;
        if (fromDate == null) {
            date = LocalDate.now();
        }

        while (date.isBefore(toDate)) {
            if (isWorkingDay(date)) {
                workingDays.add(date.toString());
            }
            date = date.plusDays(1);
        }
        if (isWorkingDay(toDate)) {
            workingDays.add(toDate.toString());
        }

        String[] result = new String[workingDays.size()];
        return workingDays.toArray(result);
    }

    public static int getNumberOfWorkingDays(LocalDate fromDate, LocalDate toDate) {
        LocalDate date = fromDate;
        if (fromDate == null) {
            date = LocalDate.now();
        }

        int count = 0;
        while (date.isBefore(toDate)) {
            if (isWorkingDay(date)) {
                count++;
            }
            date = date.plusDays(1);
        }
        if (isWorkingDay(toDate)) {
            count++;
        }

        return count;
    }

    public static boolean isWorkingDay(final LocalDate date) {
        return date.getDayOfWeek() < DateTimeConstants.SATURDAY;
    }
}
