package com.sopra.agile.cardio.back.dao;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.sopra.agile.cardio.back.model.DbSprint;
import com.sopra.agile.cardio.back.utils.converter.Converter;
import com.sopra.agile.cardio.common.exception.CardioTechnicalException;
import com.sopra.agile.cardio.common.model.Parameter;
import com.sopra.agile.cardio.common.model.Sprint;
import com.sopra.agile.cardio.common.utils.LocalDateUtils;

@Service
public class SprintDaoImpl implements SprintDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(SprintDaoImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private Converter mapper;

    private static final String SQL_COUNT = "select 'SPRINTS', count(0) from SPRINTS";
    private static final String SQL_COUNT_COMPLETED = "select 'SPRINTS_COMPLETED', count(0) from SPRINTS where END_DATE < CURRENT_DATE()";
    private static final String SQL_ALL = "select * from SPRINTS";
    private static final String SQL_ALL_COMPLETED = "select * from SPRINTS where END_DATE < CURRENT_DATE() ORDER BY START_DATE ASC";
    private static final String SQL_FIND_BY_ID = "select * from SPRINTS where id = ?";
    private static final String SQL_FIND_BY_NAME = "select * from SPRINTS where name = ?";
    private static final String SQL_FIND_BY_DAY = "select * from SPRINTS where START_DATE <= DATE '%s' AND DATE '%s' <= END_DATE ORDER BY START_DATE ASC";
    private static final String SQL_FIND_BY_DAY_NOW = "select * from SPRINTS where START_DATE <= CURRENT_DATE() AND CURRENT_DATE() <= END_DATE ORDER BY START_DATE ASC";
    private static final String SQL_INSERT = "insert into SPRINTS(NAME, START_DATE, END_DATE, GOAL, COMMITMENT, VELOCITY, CAPACITY) values (?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE = "update SPRINTS set NAME=?, START_DATE=?, END_DATE=?, GOAL=?, COMMITMENT=?, VELOCITY=?, CAPACITY=?  where ID = ?";
    private static final String SQL_DELETE = "delete from SPRINTS where id = ?";
    private static final String SQL_OVERLAP = "select * from SPRINTS where (START_DATE <= DATE '%s' AND END_DATE >= DATE '%s') OR (START_DATE >= DATE '%s' AND START_DATE <= DATE '%s' AND END_DATE >= DATE '%s')";

    public SprintDaoImpl() {
        // Empty constructor
    }

    public SprintDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Parameter count() throws CardioTechnicalException {
        LOGGER.debug("[DAO] count sprints ...");
        Parameter count = null;
        try {
            count = jdbcTemplate.queryForObject(SQL_COUNT, new ParameterMapper());
        } catch (Exception ex) {
            throw new CardioTechnicalException(DATABASE_FAILURE, ex);
        }
        return count;
    }

    @Override
    public Parameter countCompleted() throws CardioTechnicalException {
        LOGGER.debug("[DAO] count sprints completed...");
        Parameter count = null;
        try {
            count = jdbcTemplate.queryForObject(SQL_COUNT_COMPLETED, new ParameterMapper());
        } catch (Exception ex) {
            throw new CardioTechnicalException(DATABASE_FAILURE, ex);
        }
        return count;
    }

    @Override
    public List<Sprint> all() throws CardioTechnicalException {
        LOGGER.debug("[DAO] all sprints ...");
        List<Sprint> sprints = new ArrayList<Sprint>();
        try {
            List<DbSprint> dbsprints = jdbcTemplate.query(SQL_ALL, new DbSprintMapper());
            if (dbsprints != null) {
                for (DbSprint s : dbsprints) {
                    sprints.add(mapper.map(s));
                }
            }
        } catch (Exception ex) {
            throw new CardioTechnicalException(DATABASE_FAILURE, ex);
        }
        return sprints;
    }

    @Override
    public Sprint find(long id) throws CardioTechnicalException {
        LOGGER.debug("[DAO] find sprint '{}' ...", id);
        DbSprint sprint = null;
        try {
            sprint = jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new Object[] { id }, new DbSprintMapper());
        } catch (EmptyResultDataAccessException notFound) {
            LOGGER.info("No result found with id '{}'", id);
        } catch (Exception ex) {
            throw new CardioTechnicalException(DATABASE_FAILURE, ex);
        }
        return mapper.map(sprint);
    }

    @Override
    public Sprint findByName(String name) throws CardioTechnicalException {
        LOGGER.debug("[DAO] find sprint with name '{}' ...", name);
        DbSprint sprint = null;
        try {
            sprint = jdbcTemplate.queryForObject(SQL_FIND_BY_NAME, new Object[] { name }, new DbSprintMapper());
        } catch (EmptyResultDataAccessException notFound) {
            LOGGER.info("No result found with name '{}'", name);
        } catch (Exception ex) {
            throw new CardioTechnicalException(DATABASE_FAILURE, ex);
        }
        return mapper.map(sprint);
    }

    @Override
    public Sprint add(Sprint sprint) throws CardioTechnicalException {
        LOGGER.debug("[DAO] add new sprint ...");

        DbSprint dbsprint = mapper.map(sprint);

        try {
            jdbcTemplate.update(SQL_INSERT, dbsprint.getName(), LocalDateUtils.convertToDate(dbsprint.getStartDate()),
                    LocalDateUtils.convertToDate(dbsprint.getEndDate()), dbsprint.getGoal(), dbsprint.getCommitment(),
                    dbsprint.getVelocity(), dbsprint.getCapacity());
        } catch (Exception ex) {
            throw new CardioTechnicalException(DATABASE_FAILURE, ex);
        }
        return sprint;
    }

    @Override
    public Sprint update(Sprint sprint) throws CardioTechnicalException {
        LOGGER.debug("[DAO] update sprint ...");

        DbSprint dbsprint = mapper.map(sprint);
        try {
            jdbcTemplate.update(SQL_UPDATE, dbsprint.getName(), LocalDateUtils.convertToDate(dbsprint.getStartDate()),
                    LocalDateUtils.convertToDate(dbsprint.getEndDate()), dbsprint.getGoal(), dbsprint.getCommitment(),
                    dbsprint.getVelocity(), dbsprint.getCapacity(), dbsprint.getId());
        } catch (Exception ex) {
            throw new CardioTechnicalException(DATABASE_FAILURE, ex);
        }
        return sprint;
    }

    @Override
    public void remove(long id) throws CardioTechnicalException {
        LOGGER.debug("[DAO] remove sprint '{}' ...", id);
        try {
            jdbcTemplate.update(SQL_DELETE, id);
        } catch (Exception ex) {
            throw new CardioTechnicalException(DATABASE_FAILURE, ex);
        }
    }

    @Override
    public List<Sprint> findByDay(String day) throws CardioTechnicalException {
        LOGGER.info("[DAO] findByDay '{}' ...", day);

        String sql = SQL_FIND_BY_DAY_NOW;
        if (!"now".equals(day)) {
            sql = String.format(SQL_FIND_BY_DAY, day, day);
        }

        List<DbSprint> dbsprints = null;
        try {
            dbsprints = jdbcTemplate.query(sql, new DbSprintMapper());
        } catch (Exception ex) {
            throw new CardioTechnicalException(DATABASE_FAILURE, ex);
        }

        List<Sprint> sprints = new ArrayList<Sprint>();
        if (dbsprints != null) {
            for (DbSprint s : dbsprints) {
                sprints.add(mapper.map(s));
            }
        }

        return sprints;
    }

    @Override
    public List<Sprint> allCompleted() throws CardioTechnicalException {
        LOGGER.info("[DAO] allCompleted ...");

        List<DbSprint> dbsprints = null;
        try {
            dbsprints = jdbcTemplate.query(SQL_ALL_COMPLETED, new DbSprintMapper());
        } catch (Exception ex) {
            throw new CardioTechnicalException(DATABASE_FAILURE, ex);
        }
        List<Sprint> sprints = new ArrayList<Sprint>();
        if (dbsprints != null) {
            for (DbSprint s : dbsprints) {
                sprints.add(mapper.map(s));
            }
        }
        return sprints;
    }

    @Override
    public List<Sprint> overlaping(Sprint sprint) throws CardioTechnicalException {
        LOGGER.info("[DAO] overlaping ...");

        String start = sprint.getStartDate();
        String end = sprint.getEndDate();
        String sql = String.format(SQL_OVERLAP, start, start, start, end, start);

        List<DbSprint> dbsprints = null;
        try {
            dbsprints = jdbcTemplate.query(sql, new DbSprintMapper());
        } catch (Exception ex) {
            throw new CardioTechnicalException(DATABASE_FAILURE, ex);
        }
        List<Sprint> sprints = new ArrayList<Sprint>();
        if (dbsprints != null) {
            for (DbSprint s : dbsprints) {
                sprints.add(mapper.map(s));
            }
        }
        return sprints;
    }
}
