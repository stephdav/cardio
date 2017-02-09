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
    public Activity add(Activity activity) throws CardioTechnicalException, CardioFunctionalException {
        LOGGER.info("add ...");
        return activityDao.add(activity);
    }
}
