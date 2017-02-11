package com.sopra.agile.cardio.back.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sopra.agile.cardio.back.dao.ActivityDao;
import com.sopra.agile.cardio.common.exception.CardioFunctionalException;
import com.sopra.agile.cardio.common.exception.CardioTechnicalException;
import com.sopra.agile.cardio.common.model.Activity;

@Service
public class ActivityServiceImpl implements ActivityService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActivityServiceImpl.class);

    @Autowired
    private ActivityDao activityDao;

    public ActivityServiceImpl() {
        // Empty constructor
    }

    @Override
    public List<Activity> all() {
        LOGGER.info("all ...");
        List<Activity> response = null;
        try {
            response = activityDao.all();
        } catch (CardioTechnicalException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return response;
    }

    @Override
    public Activity find(String id) {
        LOGGER.info("find '{}' ...", id);
        Activity response = null;
        try {
            response = activityDao.find(id);
        } catch (CardioTechnicalException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return response;
    }

    @Override
    public Activity findByName(String name) {
        LOGGER.info("findByName '{}' ...", name);

        Activity response = null;
        try {
            response = activityDao.findByName(name);
        } catch (CardioTechnicalException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return response;
    }

    @Override
    public Activity add(Activity activity) throws CardioTechnicalException, CardioFunctionalException {
        LOGGER.info("add ...");
        checkActivityProperties(activity);
        checkActivityDuplicate(activity);
        return activityDao.add(activity);
    }

    private void checkActivityProperties(Activity activity) throws CardioFunctionalException {
        if (activity == null) {
            LOGGER.error("activity can't be null");
            throw new CardioFunctionalException("activity can't be null");
        }
        if (activity.getType() == null) {
            LOGGER.error("type is mandatory");
            throw new CardioFunctionalException("type is mandatory");
        }
        if (activity.getName() == null || activity.getName().isEmpty()) {
            LOGGER.error("name is mandatory");
            throw new CardioFunctionalException("name is mandatory");
        }
        if (activity.getStatus() == null) {
            LOGGER.error("status is mandatory");
            throw new CardioFunctionalException("status is mandatory");
        }
    }

    private void checkActivityDuplicate(Activity activity) throws CardioFunctionalException, CardioTechnicalException {
        // Looking for an activity with same name
        Activity found = findByName(activity.getName());
        if (found != null && (activity.getId() == null || !activity.getId().equals(found.getId()))) {
            LOGGER.error("An activity '{}' already exists", activity.getName());
            throw new CardioFunctionalException("activity with same name already exists");
        }
    }

}
