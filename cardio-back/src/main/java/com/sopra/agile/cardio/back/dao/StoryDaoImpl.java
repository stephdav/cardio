package com.sopra.agile.cardio.back.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.sopra.agile.cardio.common.exception.CardioTechnicalException;
import com.sopra.agile.cardio.common.model.Story;

@Service
public class StoryDaoImpl implements StoryDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(StoryDaoImpl.class);

    private static final String SQL_ALL = "select * from STORIES";
    private static final String SQL_FIND_BY_ID = "select * from STORIES where ID=?";
    private static final String SQL_FIND_BY_STATUS = "select * from STORIES where STATUS='%s'";
    private static final String SQL_INSERT = "insert into STORIES(DESCRIPTION, STATUS, LAST_UPDATE, CONTRIBUTION, ESTIMATE) values (?, ?, SYSDATE, ?, ?)";
    private static final String SQL_UPDATE = "update STORIES set DESCRIPTION=?, STATUS=?, CONTRIBUTION=?, ESTIMATE=? where ID=?";
    private static final String SQL_UPDATE_TIMESTAMP = "update STORIES set DESCRIPTION=?, STATUS=?, LAST_UPDATE=SYSDATE, CONTRIBUTION=?, ESTIMATE=? where ID=?";
    private static final String SQL_DELETE = "delete from STORIES where id=?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public StoryDaoImpl() {
        // Empty constructor
    }

    public StoryDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
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
    public Story add(Story story) throws CardioTechnicalException {
        LOGGER.debug("[DAO] add new story ...");
        try {
            jdbcTemplate.update(SQL_INSERT, story.getDescription(), story.getStatus().toString(),
                    story.getContribution(), story.getEstimate());
        } catch (Exception ex) {
            throw new CardioTechnicalException(DATABASE_FAILURE, ex);
        }
        return story;
    }

    @Override
    public Story update(Story story, boolean updateTimestamp) throws CardioTechnicalException {
        LOGGER.debug("[DAO] update story ...");
        String sql = SQL_UPDATE;
        if (updateTimestamp) {
            sql = SQL_UPDATE_TIMESTAMP;
        }
        try {
            jdbcTemplate.update(sql, story.getDescription(), story.getStatus().toString(), story.getContribution(),
                    story.getEstimate(), story.getId());
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

}
