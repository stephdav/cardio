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
import com.sopra.agile.cardio.back.utils.LocalDateUtils;
import com.sopra.agile.cardio.common.model.Chart;
import com.sopra.agile.cardio.common.model.Serie;
import com.sopra.agile.cardio.common.model.Sprint;
import com.sopra.agile.cardio.common.model.SprintData;
import com.sopra.agile.cardio.common.model.SprintDataDetails;
import com.sopra.agile.cardio.common.model.SprintDay;
import com.sopra.agile.cardio.common.model.VelocityData;

@Service
public class SprintServiceImpl implements SprintService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SprintServiceImpl.class);

    private int sample = 6;

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
    public SprintData findData(String id) {
        return getSprintDataDetails(find(id));
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

        if (nbSprints > 0) {
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
        }

        return response;
    }

    private SprintData getSprintDataDetails(Sprint sprint) {
        SprintData sprintData = new SprintData();

        SprintDataDetails details = new SprintDataDetails();
        sprintData.setDetails(details);

        if (sprint != null) {

            sprintData.setData(getDataMap(sprint));

            LocalDate startDate = new LocalDate(sprint.getStartDate());
            LocalDate endDate = new LocalDate(sprint.getEndDate());

            List<String> days = LocalDateUtils.getWorkingDaysAsList(startDate, endDate);

            Map<String, Integer> intData = new HashMap<String, Integer>();
            for (Map.Entry<String, String> entry : sprintData.getData().entrySet()) {
                intData.put(entry.getKey(), Integer.parseInt(entry.getValue()));
            }

            details.setLeftDays(LocalDateUtils.getNumberOfWorkingDays(null, endDate));
            details.setDays(days);
            details.setDone(convertMeasures(intData, days));
            computeIdealAndLeft(sprint, details);

        }

        return sprintData;
    }

    private List<Integer> convertMeasures(Map<String, Integer> measures, List<String> days) {
        List<Integer> data = new ArrayList<Integer>();
        for (String day : days) {
            data.add(measures.get(day));
        }
        return data;
    }

    private Map<String, String> getDataMap(Sprint sprint) {
        Map<String, String> values = new HashMap<String, String>();

        if (sprint != null) {
            String from = sprint.getStartDate();
            String to = sprint.getEndDate();

            List<SprintDay> measures = sprintDayDao.findBetween(from, to);
            for (SprintDay day : measures) {
                values.put(day.getDay().toString(), String.valueOf(day.getDone()));
            }
        }

        return values;
    }

    private void computeIdealAndLeft(Sprint sprint, SprintDataDetails details) {

        List<Integer> done = details.getDone();
        int commitment = sprint.getCommitment();
        int days = done.size();

        List<Integer> left = details.getLeft();
        for (Integer val : done) {
            if (val == null) {
                left.add(null);
            } else {
                left.add(commitment - val);
            }
        }

        List<Integer> ideal = details.getIdeal();
        for (int idx = 0; idx < days; idx++) {
            int val = commitment - (idx * commitment) / (days - 1);
            ideal.add(val);
        }
    }

}
