package com.sopra.agile.cardio.back.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.sopra.agile.cardio.common.exception.CardioTechnicalException;
import com.sopra.agile.cardio.common.model.Parameter;
import com.sopra.agile.cardio.common.model.Story;
import com.sopra.agile.cardio.common.model.StoryMonitor;

@Service
public class StoryDaoImpl implements StoryDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(StoryDaoImpl.class);

    private static final String SQL_COUNT = "select 'STORIES', count(0) from STORIES";
    private static final String SQL_COUNT_BY_STATUS = "select STATUS, count(0) from STORIES group by STATUS";
    private static final String SQL_ALL = "select * from STORIES";
    private static final String SQL_FIND_BY_ID = "select * from STORIES where ID=?";
    private static final String SQL_FIND_BY_STATUS = "select * from STORIES where STATUS='%s'";
    private static final String SQL_FIND_BY_SPRINT = "select * from STORIES where SPRINT='%d'";
    private static final String SQL_INSERT = "insert into STORIES(DESCRIPTION, STATUS, CONTRIBUTION, ESTIMATE, SPRINT, ASSIGNED) values (?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE = "update STORIES set DESCRIPTION=?, STATUS=?, CONTRIBUTION=?, ESTIMATE=?, SPRINT=?, ASSIGNED=? where ID=?";
    private static final String SQL_UPDATE_TIMESTAMP = "update STORIES set DESCRIPTION=?, STATUS=?, LAST_UPDATE=NOW(), CONTRIBUTION=?, ESTIMATE=?, SPRINT=?, ASSIGNED=? where ID=?";
    private static final String SQL_DELETE = "delete from STORIES where id=?";
    private static final String SQL_MONITOR = "select S.ID, S.DESCRIPTION, S.STATUS, S.CONTRIBUTION, S.ESTIMATE, U.LOGIN from STORIES S, USERS U where SPRINT='%d' AND U.ID = S.ASSIGNED";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public StoryDaoImpl() {
        // Empty constructor
    }

    public StoryDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Parameter count() throws CardioTechnicalException {
        LOGGER.debug("[DAO] count stories ...");
        Parameter count = null;
        try {
            count = jdbcTemplate.queryForObject(SQL_COUNT, new ParameterMapper());
        } catch (Exception ex) {
            throw new CardioTechnicalException(DATABASE_FAILURE, ex);
        }
        return count;
    }

    @Override
    public List<Parameter> countByStatus() throws CardioTechnicalException {
        LOGGER.debug("[DAO] count stories ...");
        List<Parameter> count = null;
        try {
            count = jdbcTemplate.query(SQL_COUNT_BY_STATUS, new ParameterMapper());
        } catch (Exception ex) {
            throw new CardioTechnicalException(DATABASE_FAILURE, ex);
        }
        return count;
    }

    @Override
    public List<Story> all() throws CardioTechnicalException {
        LOGGER.debug("[DAO] all stories ...");
        List<Story> stories = null;
        try {
            stories = jdbcTemplate.query(SQL_ALL, new StoryMapper());
        } catch (Exception ex) {
            throw new CardioTechnicalException(DATABASE_FAILURE, ex);
        }
        return stories;
    }

    @Override
    public Story find(long id) throws CardioTechnicalException {
        LOGGER.debug("[DAO] find story '{}' ...", id);
        Story story = null;
        try {
            story = jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new Object[] { id }, new StoryMapper());
        } catch (EmptyResultDataAccessException notFound) {
            LOGGER.info("No result found with id '{}'", id);
        } catch (Exception ex) {
            throw new CardioTechnicalException(DATABASE_FAILURE, ex);
        }
        return story;
    }

    @Override
    public List<Story> findByStatus(String status) throws CardioTechnicalException {
        LOGGER.debug("[DAO] find with status '{}' ...", status);
        String sql = String.format(SQL_FIND_BY_STATUS, status);
        List<Story> stories = null;
        try {
            stories = jdbcTemplate.query(sql, new StoryMapper());
        } catch (Exception ex) {
            throw new CardioTechnicalException(DATABASE_FAILURE, ex);
        }
        return stories;
    }

    @Override
    public List<Story> findBySprint(long sprintId) throws CardioTechnicalException {
        LOGGER.debug("[DAO] find with sprint '{}' ...", sprintId);
        String sql = String.format(SQL_FIND_BY_SPRINT, sprintId);
        List<Story> stories = null;
        try {
            stories = jdbcTemplate.query(sql, new StoryMapper());
        } catch (Exception ex) {
            throw new CardioTechnicalException(DATABASE_FAILURE, ex);
        }
        return stories;
    }

    @Override
    public Story add(Story story) throws CardioTechnicalException {
        LOGGER.debug("[DAO] add new story ...");
        try {
            jdbcTemplate.update(SQL_INSERT, story.getDescription(), story.getStatus().toString(),
                    story.getContribution(), story.getEstimate(), story.getSprint() == 0 ? null : story.getSprint(),
                    story.getAssignedUser() == 0 ? null : story.getAssignedUser());
        } catch (Exception ex) {
            throw new CardioTechnicalException(DATABASE_FAILURE, ex);
        }
        return story;
    }

    @Override
    public Story update(Story story) throws CardioTechnicalException {
        return update(story, true);
    }

    @Override
    public Story update(Story story, boolean updateTimestamp) throws CardioTechnicalException {
        LOGGER.debug("[DAO] update story ...");
        String sql = SQL_UPDATE;
        if (updateTimestamp) {
            LOGGER.debug("update timestamp ...");
            sql = SQL_UPDATE_TIMESTAMP;
        }
        try {
            jdbcTemplate.update(sql, story.getDescription(), story.getStatus().toString(), story.getContribution(),
                    story.getEstimate(), story.getSprint() == 0 ? null : story.getSprint(),
                    story.getAssignedUser() == 0 ? null : story.getAssignedUser(), story.getId());
        } catch (Exception ex) {
            throw new CardioTechnicalException(DATABASE_FAILURE, ex);
        }
        return story;
    }

    @Override
    public void remove(long id) throws CardioTechnicalException {
        LOGGER.info("[DAO] remove story '{}' ...", id);
        try {
            jdbcTemplate.update(SQL_DELETE, id);
        } catch (Exception ex) {
            throw new CardioTechnicalException(DATABASE_FAILURE, ex);
        }
    }

    @Override
    public List<StoryMonitor> findMonitoredStories(long sprintId) throws CardioTechnicalException {
        LOGGER.debug("[DAO] find with sprint '{}' ...", sprintId);
        String sql = String.format(SQL_MONITOR, sprintId);
        List<StoryMonitor> stories = null;
        try {
            stories = jdbcTemplate.query(sql, new StoryMonitorMapper());
        } catch (Exception ex) {
            throw new CardioTechnicalException(DATABASE_FAILURE, ex);
        }
        return stories;
    }

}
