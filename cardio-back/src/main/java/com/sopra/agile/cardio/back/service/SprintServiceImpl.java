package com.sopra.agile.cardio.back.service;

import java.util.ArrayList;
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
import com.sopra.agile.cardio.back.utils.LocalDateUtils;
import com.sopra.agile.cardio.common.exception.CardioFunctionalException;
import com.sopra.agile.cardio.common.exception.CardioTechnicalException;
import com.sopra.agile.cardio.common.model.Sprint;
import com.sopra.agile.cardio.common.model.SprintData;
import com.sopra.agile.cardio.common.model.SprintDataDetails;
import com.sopra.agile.cardio.common.model.SprintDay;

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
        List<Sprint> response = null;
        try {
            response = sprintDao.all();
        } catch (CardioTechnicalException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return response;
    }

    @Override
    public Sprint find(String id) {
        LOGGER.info("find '{}' ...", id);
        Sprint response = null;
        try {
            response = sprintDao.find(id);
        } catch (CardioTechnicalException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return response;
    }

    @Override
    public Sprint add(Sprint sprint) throws CardioTechnicalException, CardioFunctionalException {
        LOGGER.info("add ...");
        checkSprintProperties(sprint);
        checkSprintDuplicate(sprint);
        checkSprintOverlapping(sprint);
        return sprintDao.add(sprint);
    }

    @Override
    public Sprint findByName(String name) {
        LOGGER.info("findByName '{}' ...", name);

        Sprint response = null;
        try {
            response = sprintDao.findByName(name);
        } catch (CardioTechnicalException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return response;
    }

    @Override
    public List<Sprint> findByDay(String day) {
        LOGGER.info("findByDay '{}' ...", day);

        List<Sprint> response = null;
        try {
            response = sprintDao.findByDay(day);
        } catch (CardioTechnicalException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return response;
    }

    @Override
    public Sprint update(Sprint sprint) throws CardioTechnicalException, CardioFunctionalException {
        LOGGER.info("update ...");
        checkSprintProperties(sprint);
        checkSprintOverlapping(sprint);
        return sprintDao.update(sprint);
    }

    @Override
    public Sprint patch(Sprint sprint) throws CardioTechnicalException, CardioFunctionalException {
        LOGGER.info("patch ...");

        Sprint original = find(sprint.getId());
        if (sprint.getName() == null) {
            sprint.setName(original.getName());
        }
        if (sprint.getGoal() == null) {
            sprint.setGoal(original.getGoal());
        }
        if (sprint.getStartDate() == null) {
            sprint.setStartDate(original.getStartDate());
        }
        if (sprint.getEndDate() == null) {
            sprint.setEndDate(original.getEndDate());
        }
        if (sprint.getCommitment() == 0) {
            sprint.setCommitment(original.getCommitment());
        }
        if (sprint.getVelocity() == 0) {
            sprint.setVelocity(original.getVelocity());
        }

        return update(sprint);
    }

    @Override
    public SprintData findData(String id) {
        return getSprintDataDetails(find(id));
    }

    @Override
    public void updateData(String id, SprintData days) throws CardioTechnicalException, CardioFunctionalException {
        for (Map.Entry<String, String> e : days.getData().entrySet()) {
            LOGGER.info("{}: {}", e.getKey(), e.getValue());

            if (e.getValue() != null && !"".equals(e.getValue())) {
                // TODO IllegalArgumentException
                SprintDay day = new SprintDay(new LocalDate(e.getKey()), Integer.parseInt(e.getValue()));
                sprintDayDao.insertOrUpdate(day);
            } else {
                try {
                    sprintDayDao.remove(e.getKey());
                } catch (CardioTechnicalException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        }
        updateVelocity(id);
    }

    private void updateVelocity(String id) throws CardioTechnicalException, CardioFunctionalException {
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

    private void checkSprintDuplicate(Sprint sprint) throws CardioFunctionalException, CardioTechnicalException {
        if (findByName(sprint.getName()) != null) {
            LOGGER.error("A sprint '{}' already exists", sprint.getName());
            throw new CardioFunctionalException("sprint with same name already exists");
        }
    }

    private void checkSprintOverlapping(Sprint sprint) throws CardioTechnicalException, CardioFunctionalException {
        List<Sprint> conflicts = sprintDao.overlaping(sprint);
        if (conflicts != null) {
            // Remove current sprint from the list
            if (sprint.getId() != null) {
                conflicts.remove(sprint);
            }
            if (!conflicts.isEmpty()) {
                LOGGER.error("{} sprints are overlaping sprint period", conflicts.size());
                for (Sprint s : conflicts) {
                    LOGGER.debug("{} from {} to {}", s.getName(), s.getStartDate(), s.getEndDate());
                }
                throw new CardioFunctionalException("sprint overlapping");
            }
        }
    }

    private void checkSprintProperties(Sprint sprint) throws CardioFunctionalException {
        if (sprint == null) {
            LOGGER.error("sprint can't be null");
            throw new CardioFunctionalException("sprint can't be null");
        }
        if (sprint.getName() == null || sprint.getName().isEmpty()) {
            LOGGER.error("name is mandatory");
            throw new CardioFunctionalException("name is mandatory");
        }
        if (sprint.getStartDate() == null || sprint.getStartDate().isEmpty()) {
            LOGGER.error("start date is mandatory");
            throw new CardioFunctionalException("start date is mandatory");
        }
        if (sprint.getEndDate() == null || sprint.getEndDate().isEmpty()) {
            LOGGER.error("enddate is mandatory");
            throw new CardioFunctionalException("end date is mandatory");
        }
    }
}
