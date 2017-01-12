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
import com.sopra.agile.cardio.common.model.Sprint;
import com.sopra.agile.cardio.common.utils.LocalDateUtils;

@Service
public class SprintDaoImpl implements SprintDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(SprintDaoImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private Converter mapper;

    private static final String DATABASE_FAILURE = "Failure when accessing database";

    private static final String SQL_ALL = "select * from SPRINTS";
    private static final String SQL_ALL_COMPLETED = "select * from SPRINTS where END_DATE <= SYSDATE ORDER BY START_DATE ASC";
    private static final String SQL_FIND_BY_ID = "select * from SPRINTS where id = ?";
    private static final String SQL_FIND_BY_NAME = "select * from SPRINTS where name = ?";
    private static final String SQL_FIND_BY_DAY = "select * from SPRINTS where START_DATE <= DATE '%s' AND DATE '%s' <= END_DATE ORDER BY START_DATE ASC";
    private static final String SQL_FIND_BY_DAY_NOW = "select * from SPRINTS where START_DATE <= SYSDATE AND SYSDATE <= END_DATE ORDER BY START_DATE ASC";
    private static final String SQL_INSERT = "insert into SPRINTS(ID, NAME, START_DATE, END_DATE, GOAL, COMMITMENT, VELOCITY) values (?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE = "update SPRINTS set NAME=?, START_DATE=?, END_DATE=?, GOAL=?, COMMITMENT=?, VELOCITY=?  where ID = ?";
    private static final String SQL_DELETE = "delete from SPRINTS where id = ?";
    private static final String SQL_OVERLAP = "select * from SPRINTS where (START_DATE <= DATE '%s' AND END_DATE >= DATE '%s') OR (START_DATE >= DATE '%s' AND START_DATE <= DATE '%s' AND END_DATE >= DATE '%s')";

    public SprintDaoImpl() {
        // Empty constructor
    }

    public SprintDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Sprint> all() throws CardioTechnicalException {
        LOGGER.info("[DAO] all ...");

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
    public Sprint find(String id) throws CardioTechnicalException {
        LOGGER.info("[DAO] find '{}' ...", id);

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
    public Sprint add(Sprint sprint) throws CardioTechnicalException {
        LOGGER.info("[DAO] add ...");

        sprint.setId(UIDGenerator.getUniqueId("SPR"));
        DbSprint dbsprint = mapper.map(sprint);

        try {
            jdbcTemplate.update(SQL_INSERT, dbsprint.getId(), dbsprint.getName(),
                    LocalDateUtils.convertToDate(dbsprint.getStartDate()),
                    LocalDateUtils.convertToDate(dbsprint.getEndDate()), dbsprint.getGoal(), dbsprint.getCommitment(),
                    dbsprint.getVelocity());
        } catch (Exception ex) {
            throw new CardioTechnicalException(DATABASE_FAILURE, ex);
        }
        return sprint;
    }

    @Override
    public Sprint findByName(String name) throws CardioTechnicalException {
        LOGGER.info("[DAO] findByName '{}' ...", name);

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
    public Sprint update(Sprint sprint) throws CardioTechnicalException {
        LOGGER.info("[DAO] update ...");

        DbSprint dbsprint = mapper.map(sprint);
        try {
            jdbcTemplate.update(SQL_UPDATE, dbsprint.getName(), LocalDateUtils.convertToDate(dbsprint.getStartDate()),
                    LocalDateUtils.convertToDate(dbsprint.getEndDate()), dbsprint.getGoal(), dbsprint.getCommitment(),
                    dbsprint.getVelocity(), dbsprint.getId());
        } catch (Exception ex) {
            throw new CardioTechnicalException(DATABASE_FAILURE, ex);
        }
        return sprint;
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
