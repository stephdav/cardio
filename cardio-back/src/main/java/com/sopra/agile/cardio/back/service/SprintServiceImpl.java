package com.sopra.agile.cardio.back.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

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
import com.sopra.agile.cardio.common.model.Serie;
import com.sopra.agile.cardio.common.model.Sprint;
import com.sopra.agile.cardio.common.model.SprintData;
import com.sopra.agile.cardio.common.model.SprintDay;
import com.sopra.agile.cardio.common.model.VelocityData;

@Service
public class SprintServiceImpl implements SprintService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SprintServiceImpl.class);

    private static int sample = 6;

    @Autowired
    private ConfigService configSvc;

    @Autowired
    private SprintDao sprintDao;

    @Autowired
    private SprintDayDao sprintDayDao;

    public SprintServiceImpl() {
        // Empty constructor
    }

    @PostConstruct
    public void init() {
        sample = configSvc.getIntProperty("statistic.sprints.sample");
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
    public Sprint update(Sprint sprint) {
        LOGGER.info("update ...");
        return sprintDao.update(sprint);
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
    public Chart data(String id) {
        return sprintData(find(id));
    }

    @Override
    public Chart burndown() {
        return sprintData(currentSprint());
    }

    private Chart sprintData(Sprint sprint) {
        Chart chart = new Chart();

        // int nbDays = 0;
        LocalDate startDate = null;
        LocalDate endDate = null;

        if (sprint != null) {
            startDate = new LocalDate(sprint.getStartDate());
            endDate = new LocalDate(sprint.getEndDate());

            String[] days = LocalDateUtils.getWorkingDays(startDate, endDate);
            chart.setDays(days);

            chart.setSeries(Arrays.asList(computeIdealBurndown(days.length, sprint.getCommitment()),
                    convertMeasures("real", getMeasures(sprint), days),
                    convertMeasures("done", getDoneMeasures(sprint), days)));
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

    private Map<String, Double> getMeasures(Sprint sprint) {
        String from = sprint.getStartDate();
        String to = sprint.getEndDate();
        int commitment = sprint.getCommitment();

        Map<String, Double> values = new HashMap<String, Double>();

        List<SprintDay> measures = sprintDayDao.findBetween(from, to);
        for (SprintDay day : measures) {
            values.put(day.getDay().toString(), Double.valueOf(commitment - day.getDone()));
        }
        return values;
    }

    private Map<String, Double> getDoneMeasures(Sprint sprint) {
        String from = sprint.getStartDate();
        String to = sprint.getEndDate();

        Map<String, Double> values = new HashMap<String, Double>();

        List<SprintDay> measures = sprintDayDao.findBetween(from, to);
        for (SprintDay day : measures) {
            values.put(day.getDay().toString(), Double.valueOf(day.getDone()));
        }
        return values;
    }

    private Serie convertMeasures(String serieName, Map<String, Double> measures, String[] days) {
        Double[] data = new Double[days.length];
        int idx = 0;
        for (String day : days) {
            data[idx++] = measures.get(day);
        }
        return new Serie(serieName, data);
    }

    @Override
    public void updateData(String id, SprintData days) {
        for (Map.Entry<String, String> e : days.getData().entrySet()) {
            LOGGER.info("{}: {}", e.getKey(), e.getValue());

            if (e.getValue() != null && !"".equals(e.getValue())) {
                // TODO IllegalArgumentException
                SprintDay day = new SprintDay(new LocalDate(e.getKey()), Integer.parseInt(e.getValue()));
                sprintDayDao.insertOrUpdate(day);
            } else {
                sprintDayDao.remove(e.getKey());
            }
        }
        updateVelocity(id);
    }

    private void updateVelocity(String id) {
        Sprint sprint = find(id);
        String from = sprint.getStartDate();
        String to = sprint.getEndDate();

        SprintDay day = sprintDayDao.findLastBetween(from, to);

        int velocity = 0;
        if (day != null) {
            velocity = day.getDone();
        }
        sprint.setVelocity(velocity);
        update(sprint);
    }

    @Override
    public Chart burnup() {
        Chart chart = new Chart();

        List<Sprint> sprints = sprintDao.allCompleted();

        int nbSprints = sprints.size();

        String[] days = new String[nbSprints];
        Double[] data = new Double[nbSprints];

        int idx = 0;
        for (Sprint s : sprints) {
            days[idx] = s.getName();
            data[idx] = Double.valueOf(s.getVelocity());
            if (idx > 0) {
                data[idx] = data[idx] + data[idx - 1];
            }
            idx++;
        }
        chart.setSeries(Arrays.asList(new Serie("done", data)));

        chart.setDays(days);
        return chart;
    }

    @Override
    public VelocityData velocity() {
        VelocityData response = new VelocityData();

        List<Sprint> tail = sprintDao.allCompleted();
        // Keep only 10 last sprints
        List<Sprint> sprints = tail.subList(Math.max(tail.size() - sample, 0), tail.size());

        int nbSprints = sprints.size();
        String[] days = new String[nbSprints];
        List<Integer> values = new ArrayList<Integer>();
        Integer[] data = new Integer[nbSprints];

        int idx = 0;
        int average = 0;
        int overCommit = 0;

        for (Sprint s : sprints) {
            days[idx] = s.getName();
            data[idx] = Integer.valueOf(s.getVelocity());

            average += data[idx];
            values.add(data[idx]);
            if (data[idx] < Integer.valueOf(s.getCommitment())) {
                overCommit++;
            }

            idx++;
        }

        response.setNames(days);
        response.setData(data);

        // sort values
        values.sort(Comparator.naturalOrder());

        int nb = values.size() / 2;

        List<Integer> worstList = values.subList(0, Math.min(values.size(), nb));
        int worst = 0;
        for (int x : worstList) {
            worst += x;
        }
        worst /= nb;

        List<Integer> bestList = values.subList(Math.max(values.size() - nb, 0), values.size());
        int best = 0;
        for (int x : bestList) {
            best += x;
        }
        best /= nb;

        response.setWorst(worst);
        response.setAverage(average / idx);
        response.setBest(best);
        response.setOverCommit(100 * overCommit / idx);

        return response;
    }
}
