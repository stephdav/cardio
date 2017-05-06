package com.sopra.agile.cardio.back.service;

import java.util.List;

import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sopra.agile.cardio.back.dao.StoryDao;
import com.sopra.agile.cardio.back.utils.Fields;
import com.sopra.agile.cardio.common.exception.CardioFunctionalException;
import com.sopra.agile.cardio.common.exception.CardioTechnicalException;
import com.sopra.agile.cardio.common.model.Parameter;
import com.sopra.agile.cardio.common.model.Story;
import com.sopra.agile.cardio.common.model.StoryStatus;

@Service
public class StoryServiceImpl implements StoryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StoryServiceImpl.class);

    private static DateTimeFormatter DATE_EXPORT_FORMAT = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");

    private static final String SQL_EXPORT = "INSERT INTO STORIES(DESCRIPTION,STATUS,LAST_UPDATE,CONTRIBUTION,ESTIMATE,ASSIGNED) VALUES ('%s', '%s', TIMESTAMP '%s', %d, %d, %d);\n";
    private static final String SQL_EXPORT_WITHOUT_USER = "INSERT INTO STORIES(DESCRIPTION,STATUS,LAST_UPDATE,CONTRIBUTION,ESTIMATE) VALUES ('%s', '%s', TIMESTAMP '%s', %d, %d);\n";

    @Autowired
    private StoryDao storyDao;

    public StoryServiceImpl() {
        // Empty constructor
    }

    @Override
    public Parameter count() throws CardioTechnicalException {
        LOGGER.debug("[SVC] count ...");
        Parameter param = storyDao.count();
        if (param == null) {
            param = new Parameter("STORIES", "0");
        }
        return param;
    }

    @Override
    public List<Parameter> countByStatus() throws CardioTechnicalException {
        LOGGER.debug("[SVC] count by status ...");
        return storyDao.countByStatus();
    }

    @Override
    public List<Story> all() throws CardioTechnicalException {
        LOGGER.debug("[SVC] all ...");
        return storyDao.all();
    }

    @Override
    public Story find(String id) throws CardioTechnicalException {
        LOGGER.debug("[SVC] find story '{}' ...", id);
        Story response = null;
        try {
            response = find(Long.parseLong(id));
        } catch (NumberFormatException e) {
            throw new CardioTechnicalException("Bad story identifier", e);
        }
        return response;
    }

    private Story find(long id) throws CardioTechnicalException {
        return storyDao.find(id);
    }

    @Override
    public Story add(Story story) throws CardioTechnicalException, CardioFunctionalException {
        LOGGER.debug("[SVC] add story ...");
        checkStoryProperties(story);
        return storyDao.add(story);
    }

    @Override
    public Story update(Story story) throws CardioTechnicalException, CardioFunctionalException {
        LOGGER.debug("[SVC] update story ...");
        return update(story, true);
    }

    @Override
    public Story patch(Story story) throws CardioTechnicalException, CardioFunctionalException {
        LOGGER.debug("[SVC] patch story ...");

        boolean updateTimestamp = false;

        Story original = find(story.getId());
        if (story.getDescription() == null) {
            story.setDescription(original.getDescription());
        }
        if (story.getStatus() == null) {
            story.setStatus(original.getStatus());
        } else {
            if (!story.getStatus().equals(original.getStatus())) {
                updateTimestamp = true;
            }
        }
        if (story.getContribution() == -1) {
            story.setContribution(original.getContribution());
        }
        if (story.getEstimate() == -1) {
            story.setEstimate(original.getEstimate());
        }

        return update(story, updateTimestamp);
    }

    private Story update(Story story, boolean updateTimestamp)
            throws CardioTechnicalException, CardioFunctionalException {
        checkStoryProperties(story);
        return storyDao.update(story, updateTimestamp);
    }

    @Override
    public Story patch(String id, String status, String contribution, String estimate, String assignedUser)
            throws CardioTechnicalException, CardioFunctionalException {
        LOGGER.debug("[SVC] patch story ...");

        Story story = find(id);
        boolean updateTimestamp = false;

        if (status != null) {
            if (!story.getStatus().toString().equals(status)) {
                updateTimestamp = true;
            }
            story.setStatus(StoryStatus.valueOf(status));
        }
        if (contribution != null) {
            story.setContribution(Integer.parseInt(contribution));
        }
        if (estimate != null) {
            story.setEstimate(Integer.parseInt(estimate));
        }
        if (assignedUser != null) {
            story.setAssignedUser(Long.parseLong(assignedUser));
        }
        return update(story, updateTimestamp);
    }

    private void checkStoryProperties(Story story) throws CardioFunctionalException {

        if (story == null) {
            LOGGER.error("story can't be null");
            throw new CardioFunctionalException("story can't be null");
        }
        if (story.getStatus() == null) {
            LOGGER.error("status is mandatory");
            throw new CardioFunctionalException("status is mandatory");
        }
        Fields.checkField("description", story.getDescription(), 0, 256);
    }

    @Override
    public String remove(String id) throws CardioTechnicalException {
        LOGGER.info("remove '{}' ...", id);
        Story story = find(id);
        if (story != null) {
            storyDao.remove(Long.parseLong(id));
        }
        return id;
    }

    @Override
    public String export() throws CardioTechnicalException {
        List<Story> stories = all();
        StringBuffer sb = new StringBuffer();

        for (Story us : stories) {
            if (us.getAssignedUser() == null) {
                sb.append(String.format(SQL_EXPORT_WITHOUT_USER, escapeSpecialCharacters(us.getDescription()),
                        us.getStatus().toString(), convertLocalDateTime(us.getLastUpdate()), us.getContribution(),
                        us.getEstimate()));
            } else {
                sb.append(String.format(SQL_EXPORT, escapeSpecialCharacters(us.getDescription()),
                        us.getStatus().toString(), convertLocalDateTime(us.getLastUpdate()), us.getContribution(),
                        us.getEstimate(), us.getAssignedUser()));
            }
        }

        return sb.toString();
    }

    private String escapeSpecialCharacters(String txt) {
        String escapedTxt = null;
        if (txt != null) {
            escapedTxt = txt.replaceAll("'", "''");
        }
        return escapedTxt;
    }

    private String convertLocalDateTime(LocalDateTime dateTime) {
        String stringDate = null;
        if (dateTime != null) {
            stringDate = DATE_EXPORT_FORMAT.print(dateTime);
        }
        return stringDate;
    }
}
