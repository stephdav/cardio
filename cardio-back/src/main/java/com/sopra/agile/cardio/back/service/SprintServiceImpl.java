package com.sopra.agile.cardio.back.service;

import java.util.List;

import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sopra.agile.cardio.back.dao.SprintDao;
import com.sopra.agile.cardio.back.model.Parameter;
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
            value = String.valueOf(computeLeftWorkingDays(current));
        }
        return new Parameter("left-days", value);
    }

    private int computeLeftWorkingDays(Sprint sprint) {
        int count = 1;

        LocalDate end = new LocalDate(sprint.getEndDate());
        LocalDate date = LocalDate.now();

        while (date.isBefore(end)) {
            if (isWorkingDay(date)) {
                count++;
            }
            date = date.plusDays(1);
        }
        return count;
    }

    private boolean isWorkingDay(LocalDate date) {
        return date.getDayOfWeek() < DateTimeConstants.SATURDAY;
    }

}
