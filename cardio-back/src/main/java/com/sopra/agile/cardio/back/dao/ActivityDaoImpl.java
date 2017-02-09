package com.sopra.agile.cardio.back.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.sopra.agile.cardio.common.exception.CardioTechnicalException;
import com.sopra.agile.cardio.common.model.Activity;

@Service
public class ActivityDaoImpl implements ActivityDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActivityDaoImpl.class);

    private static final String SQL_ALL = "select * from ACTIVITIES";
    private static final String SQL_FIND_BY_ID = "select * from ACTIVITIES where id = ?";
    private static final String SQL_INSERT = "insert into ACTIVITIES(ID, NAME, DESCRIPTION, STATUS) values (?, ?, ?, ?)";
    private static final String SQL_DELETE = "delete from ACTIVITIES where id = ?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public ActivityDaoImpl() {
        // Empty constructor
    }

    public ActivityDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Activity> all() throws CardioTechnicalException {
        LOGGER.info("[DAO] all ...");
        List<Activity> sprints = null;
        try {
            sprints = jdbcTemplate.query(SQL_ALL, new ActivityMapper());
        } catch (Exception ex) {
            throw new CardioTechnicalException(DATABASE_FAILURE, ex);
        }
        return sprints;
    }

    @Override
    public Activity find(String id) throws CardioTechnicalException {
        LOGGER.info("[DAO] find '{}' ...", id);
        Activity activity = null;
        try {
            activity = jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new Object[] { id }, new ActivityMapper());
        } catch (EmptyResultDataAccessException notFound) {
            LOGGER.info("No result found with id '{}'", id);
        } catch (Exception ex) {
            throw new CardioTechnicalException(DATABASE_FAILURE, ex);
        }
        return activity;
    }

    @Override
    public Activity add(Activity activity) throws CardioTechnicalException {
        LOGGER.info("[DAO] add ...");
        activity.setId(UIDGenerator.getUniqueId("ACT"));
        try {
            jdbcTemplate.update(SQL_INSERT, activity.getId(), activity.getName(), activity.getDescription(),
                    activity.getStatus().toString());
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new CardioTechnicalException(DATABASE_FAILURE, ex);
        }
        return activity;
    }

    @Override
    public void remove(String id) throws CardioTechnicalException {
        LOGGER.info("[DAO] remove '{}' ...", id);
        try {
            jdbcTemplate.update(SQL_DELETE, id);
        } catch (Exception ex) {
            throw new CardioTechnicalException(DATABASE_FAILURE, ex);
        }
    }

}
