package com.sopra.agile.cardio.back.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.sopra.agile.cardio.common.exception.CardioTechnicalException;
import com.sopra.agile.cardio.common.model.Tasks;
import com.sopra.agile.cardio.common.utils.LocalDateUtils;

@Service
public class TasksDaoImpl implements TasksDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(TasksDaoImpl.class);

    private static final String SQL_ALL = "select * from TASKS";
    private static final String SQL_FIND_BY_STORY = "select * from TASKS where ID=? ORDER BY DAY ASC";
    private static final String SQL_MERGE = "merge into TASKS(ID, DAY, SPECIF, ALM, QUALIF, TODO, PENDING, DONE) key(DAY) values (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_DELETE_BY_STORY = "delete from TASKS where ID=?";
    private static final String SQL_FIND_BETWEEN = "select * from TASKS where DATE '%s' <= DAY AND DAY <= DATE '%s' ORDER BY DAY ASC";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public TasksDaoImpl() {
        // Empty constructor
    }

    public TasksDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Tasks> all() throws CardioTechnicalException {
        LOGGER.debug("[DAO] all tasks ...");
        List<Tasks> tasks = null;
        try {
            tasks = jdbcTemplate.query(SQL_ALL, new TasksMapper());
        } catch (Exception ex) {
            throw new CardioTechnicalException(DATABASE_FAILURE, ex);
        }
        return tasks;
    }

    @Override
    public List<Tasks> findByStory(long id) throws CardioTechnicalException {
        LOGGER.debug("[DAO] all tasks for sprint '{}' ...", id);
        List<Tasks> tasks = null;
        try {
            tasks = jdbcTemplate.query(SQL_FIND_BY_STORY, new Object[] { id }, new TasksMapper());
        } catch (Exception ex) {
            throw new CardioTechnicalException(DATABASE_FAILURE, ex);
        }
        return tasks;
    }

    @Override
    public Tasks add(Tasks tasks) throws CardioTechnicalException {
        return insertOrUpdate(tasks);
    }

    @Override
    public Tasks insertOrUpdate(Tasks tasks) throws CardioTechnicalException {
        LOGGER.info("[DAO] insertOrUpdate ...");
        try {
            jdbcTemplate.update(SQL_MERGE, tasks.getId(), LocalDateUtils.convertToDate(tasks.getDay()), tasks.isSpe(),
                    tasks.isAlm(), tasks.isQua(), tasks.getTodo(), tasks.getPending(), tasks.getDone());
        } catch (Exception ex) {
            throw new CardioTechnicalException(DATABASE_FAILURE, ex);
        }
        return tasks;
    }

    @Override
    public void removeByStory(long day) throws CardioTechnicalException {
        LOGGER.info("[DAO] remove for sprint '{}' ...", day);
        try {
            jdbcTemplate.update(SQL_DELETE_BY_STORY, day);
        } catch (Exception ex) {
            throw new CardioTechnicalException(DATABASE_FAILURE, ex);
        }
    }

    @Override
    public List<Tasks> findBetween(String start, String end) throws CardioTechnicalException {
        LOGGER.info("[DAO] findBetween {} and {} ...", start, end);
        List<Tasks> days = null;
        try {
            String sql = String.format(SQL_FIND_BETWEEN, start, end);
            days = jdbcTemplate.query(sql, new TasksMapper());
        } catch (Exception ex) {
            throw new CardioTechnicalException(DATABASE_FAILURE, ex);
        }
        return days;
    }

}
