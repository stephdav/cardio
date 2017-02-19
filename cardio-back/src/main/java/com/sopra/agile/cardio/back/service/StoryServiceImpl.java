package com.sopra.agile.cardio.back.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sopra.agile.cardio.back.dao.StoryDao;
import com.sopra.agile.cardio.back.utils.Fields;
import com.sopra.agile.cardio.common.exception.CardioFunctionalException;
import com.sopra.agile.cardio.common.exception.CardioTechnicalException;
import com.sopra.agile.cardio.common.model.Story;

@Service
public class StoryServiceImpl implements StoryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StoryServiceImpl.class);

    @Autowired
    private StoryDao storyDao;

    public StoryServiceImpl() {
        // Empty constructor
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
        checkStoryProperties(story);
        return storyDao.update(story);
    }

    @Override
    public Story patch(Story story) throws CardioTechnicalException, CardioFunctionalException {
        LOGGER.debug("[SVC] patch story ...");

        Story original = find(story.getId());
        if (story.getDescription() == null) {
            story.setDescription(original.getDescription());
        }
        if (story.getStatus() == null) {
            story.setStatus(original.getStatus());
        }
        if (story.getContribution() == -1) {
            story.setContribution(original.getContribution());
        }
        if (story.getEstimate() == -1) {
            story.setEstimate(original.getEstimate());
        }

        return update(story);
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

}
