package com.sopra.agile.cardio.back.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sopra.agile.cardio.back.dao.SprintDao;
import com.sopra.agile.cardio.back.dao.SprintDayDao;
import com.sopra.agile.cardio.back.model.Parameter;
import com.sopra.agile.cardio.back.utils.LocalDateUtils;
import com.sopra.agile.cardio.common.model.Chart;
import com.sopra.agile.cardio.common.model.SprintDay;
import com.sopra.agile.cardio.common.model.Serie;
import com.sopra.agile.cardio.common.model.Sprint;

@Service
public class SprintServiceImpl implements SprintService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SprintServiceImpl.class);

    @Autowired
    private SprintDao sprintDao;

    @Autowired
    private SprintDayDao sprintDayDao;

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
            value = String.valueOf(LocalDateUtils.getNumberOfWorkingDays(null, new LocalDate(current.getEndDate())));
        }
        return new Parameter("left-days", value);
    }

    @Override
    public Chart burndown() {
        Chart chart = new Chart();

        Sprint current = currentSprint();

        // int nbDays = 0;
        LocalDate startDate = null;
        LocalDate endDate = null;

        if (current != null) {
            startDate = new LocalDate(current.getStartDate());
            endDate = new LocalDate(current.getEndDate());

            String[] days = LocalDateUtils.getWorkingDays(startDate, endDate);
            chart.setDays(days);

            chart.setSeries(Arrays.asList(computeIdealBurndown(days.length, current.getCommitment()),
                    convertMeasures(getMeasures(current.getStartDate(), current.getEndDate()), days)));
        }
        return chart;
    }

    private Serie computeIdealBurndown(int days, int commitment) {
        Double[] data = new Double[days];

        for (int idx = 0; idx < days; idx++) {
            data[idx] = commitment - (idx * commitment) * 1d / (days - 1);
        }
        return new Serie("ideal", data);
    }

    private Map<String, Double> getMeasures(String from, String to) {
        Map<String, Double> values = new HashMap<String, Double>();

        List<SprintDay> measures = sprintDayDao.findBetween(from, to);
        for (SprintDay day : measures) {
            values.put(day.getDay().toString(), Double.valueOf(day.getCommitmentLeft()));
        }
        return values;
    }

    private Serie convertMeasures(Map<String, Double> measures, String[] days) {
        Double[] data = new Double[days.length];
        int idx = 0;
        for (String day : days) {
            data[idx++] = measures.get(day);
        }
        return new Serie("real", data);
    }

}
