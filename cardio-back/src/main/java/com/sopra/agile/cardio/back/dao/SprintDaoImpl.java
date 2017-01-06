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

    public SprintDaoImpl() {
        // Empty constructor
    }

    public SprintDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Sprint> all() throws CardioTechnicalException {
        LOGGER.info("[DAO] all ...");
        String sql = "select * from SPRINTS";
        List<DbSprint> dbsprints = jdbcTemplate.query(sql, new DbSprintMapper());

        List<Sprint> sprints = new ArrayList<Sprint>();
        for (DbSprint s : dbsprints) {
            sprints.add(mapper.map(s));
        }
        return sprints;
    }

    @Override
    public Sprint find(String id) throws CardioTechnicalException {
        LOGGER.info("[DAO] find '{}' ...", id);
        String sql = "select * from SPRINTS where id = ?";
        DbSprint sprint = null;
        try {
            sprint = jdbcTemplate.queryForObject(sql, new Object[] { id }, new DbSprintMapper());
        } catch (EmptyResultDataAccessException notFound) {
            LOGGER.info("No result found with id '{}'", id);
        }
        return mapper.map(sprint);
    }

    @Override
    public Sprint add(Sprint sprint) throws CardioTechnicalException {
        LOGGER.info("[DAO] add ...");

        sprint.setId(UIDGenerator.getUniqueId("SPR"));
        DbSprint dbsprint = mapper.map(sprint);

        String sql = "insert into SPRINTS(ID, NAME, START_DATE, END_DATE, GOAL, COMMITMENT, VELOCITY) values (?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, dbsprint.getId(), dbsprint.getName(),
                LocalDateUtils.convertToDate(dbsprint.getStartDate()),
                LocalDateUtils.convertToDate(dbsprint.getEndDate()), dbsprint.getGoal(), dbsprint.getCommitment(),
                dbsprint.getVelocity());
        return sprint;
    }

    @Override
    public Sprint findByName(String name) throws CardioTechnicalException {
        LOGGER.info("[DAO] findByName '{}' ...", name);
        String sql = "select * from SPRINTS where name = ?";
        DbSprint sprint = null;
        try {
            sprint = jdbcTemplate.queryForObject(sql, new Object[] { name }, new DbSprintMapper());
        } catch (EmptyResultDataAccessException notFound) {
            LOGGER.info("No result found with id '{}'", name);
        }
        return mapper.map(sprint);
    }

    @Override
    public Sprint update(Sprint sprint) throws CardioTechnicalException {
        LOGGER.info("[DAO] update ...");

        DbSprint dbsprint = mapper.map(sprint);

        String sql = "update SPRINTS set NAME=?, START_DATE=?, END_DATE=?, GOAL=?, COMMITMENT=?, VELOCITY=?  where ID = ?";
        jdbcTemplate.update(sql, dbsprint.getName(), LocalDateUtils.convertToDate(dbsprint.getStartDate()),
                LocalDateUtils.convertToDate(dbsprint.getEndDate()), dbsprint.getGoal(), dbsprint.getCommitment(),
                dbsprint.getVelocity(), dbsprint.getId());
        return sprint;
    }

    @Override
    public void remove(String id) {
        LOGGER.info("[DAO] remove '{}' ...", id);
        String SQL = "delete from SPRINTS where id = ?";
        jdbcTemplate.update(SQL, id);
    }

    @Override
    public Sprint current() throws CardioTechnicalException {
        LOGGER.info("[DAO] current ...");
        String sql = "select * from SPRINTS where START_DATE <= SYSDATE AND SYSDATE <= END_DATE ORDER BY START_DATE ASC";

        List<DbSprint> dbsprints = jdbcTemplate.query(sql, new DbSprintMapper());

        Sprint sprint = null;
        if (dbsprints != null && !dbsprints.isEmpty()) {
            sprint = mapper.map(dbsprints.get(0));
        }

        return sprint;
    }

    @Override
    public List<Sprint> allCompleted() throws CardioTechnicalException {
        LOGGER.info("[DAO] allCompleted ...");
        String sql = "select * from SPRINTS where END_DATE <= SYSDATE ORDER BY START_DATE ASC";
        List<DbSprint> dbsprints = jdbcTemplate.query(sql, new DbSprintMapper());

        List<Sprint> sprints = new ArrayList<Sprint>();
        for (DbSprint s : dbsprints) {
            sprints.add(mapper.map(s));
        }
        return sprints;
    }

}
