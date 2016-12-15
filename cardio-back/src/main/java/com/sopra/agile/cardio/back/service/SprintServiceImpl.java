package com.sopra.agile.cardio.back.service;

import java.util.Arrays;
import java.util.List;

import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sopra.agile.cardio.back.dao.SprintDao;
import com.sopra.agile.cardio.back.model.Parameter;
import com.sopra.agile.cardio.common.model.Chart;
import com.sopra.agile.cardio.common.model.Serie;
import com.sopra.agile.cardio.common.model.Sprint;

@Service
public class SprintServiceImpl implements SprintService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SprintServiceImpl.class);

    @Autowired
    private SprintDao sprintDao;

    public SprintServiceImpl() {
        // Empty constructor
    }

    @Override
    public List<Sprint> all() {
        LOGGER.info("all ...");
        return sprintDao.all();
    }

    @Override
    public Sprint find(String id) {
        LOGGER.info("find '{}' ...", id);
        return sprintDao.find(id);
    }

    @Override
    public Sprint add(Sprint sprint) {
        LOGGER.info("add ...");
        return sprintDao.add(sprint);
    }

    @Override
    public Sprint currentSprint() {
        LOGGER.info("currentSprint ...");
        return sprintDao.current();
    }

    @Override
    public Parameter leftDays() {
        String value = "-";
        Sprint current = currentSprint();
        if (current != null) {
            value = String.valueOf(computeLeftWorkingDays(current, null));
        }
        return new Parameter("left-days", value);
    }

    @Override
    public Chart burndown() {
        Chart chart = new Chart();

        Sprint current = currentSprint();

        int nbDays = 0;
        LocalDate startDate = null;
        LocalDate endDate = null;

        if (current != null) {
            startDate = new LocalDate(current.getStartDate());
            endDate = new LocalDate(current.getEndDate());
            nbDays = computeLeftWorkingDays(current, startDate);

            String[] days = computeDays(current, nbDays, endDate);
            chart.setDays(days);

            chart.setSeries(Arrays.asList(computeIdealBurndown(nbDays, current.getCommitment())));
        }
        return chart;
    }

    private String[] computeDays(Sprint current, int nbDays, LocalDate endDate) {
        String[] days = new String[nbDays];
        int idx = 0;
        LocalDate dayOfSprint = new LocalDate(current.getStartDate());
        while (dayOfSprint.isBefore(endDate)) {
            if (isWorkingDay(dayOfSprint)) {
                days[idx++] = dayOfSprint.toString();
            }
            dayOfSprint = dayOfSprint.plusDays(1);
        }
        if (isWorkingDay(endDate)) {
            days[idx++] = endDate.toString();
        }
        return days;
    }

    private Serie computeIdealBurndown(int days, int commitment) {
        Double[] data = new Double[days];

        for (int idx = 0; idx < days; idx++) {
            data[idx] = commitment - (idx * commitment) * 1d / (days - 1);
        }
        return new Serie("ideal", data);
    }

    private Serie computeFakeReal(int days, int commitment) {
        Double[] data = new Double[days / 2];

        for (int idx = 0; idx < days / 2; idx++) {
            data[idx] = (commitment - idx) * 1d;
        }
        return new Serie("real", data);
    }

    private int computeLeftWorkingDays(Sprint sprint, LocalDate from) {
        LocalDate date = from;
        if (from == null) {
            date = LocalDate.now();
        }
        LocalDate end = new LocalDate(sprint.getEndDate());

        int count = 1;
        while (date.isBefore(end)) {
            if (isWorkingDay(date)) {
                count++;
            }
            date = date.plusDays(1);
        }
        return count;
    }

    private boolean isWorkingDay(final LocalDate date) {
        return date.getDayOfWeek() < DateTimeConstants.SATURDAY;
    }

}
