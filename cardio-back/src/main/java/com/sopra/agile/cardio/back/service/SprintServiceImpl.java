package com.sopra.agile.cardio.back.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sopra.agile.cardio.back.dao.SprintDao;
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

}
