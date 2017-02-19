package com.sopra.agile.cardio.back.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.sopra.agile.cardio.common.exception.CardioTechnicalException;
import com.sopra.agile.cardio.common.model.SprintDay;
import com.sopra.agile.cardio.common.utils.LocalDateUtils;

@Service
public class SprintDayDaoImpl implements SprintDayDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(SprintDayDaoImpl.class);

    private static final String SQL_ALL = "select * from SPRINT_DAYS";
    private static final String SQL_FIND = "select * from SPRINT_DAYS where day=?";
    private static final String SQL_MERGE = "merge into SPRINT_DAYS(DAY, DONE) key(DAY) values (?, ?)";
    private static final String SQL_DELETE = "delete from SPRINT_DAYS where day=?";
    private static final String SQL_FIND_BETWEEN = "select * from SPRINT_DAYS where DATE '%s' <= DAY AND DAY <= DATE '%s' ORDER BY DAY ASC";
    private static final String SQL_FIND_LAST_BETWEEN = "select * from SPRINT_DAYS where DATE '%s' <= DAY AND DAY <= DATE '%s' ORDER BY DAY DESC";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public SprintDayDaoImpl() {
        // Empty constructor
    }

    public SprintDayDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<SprintDay> all() throws CardioTechnicalException {
        LOGGER.debug("[DAO] all users ...");
        List<SprintDay> days = null;
        try {
            days = jdbcTemplate.query(SQL_ALL, new SprintDayMapper());
        } catch (Exception ex) {
            throw new CardioTechnicalException(DATABASE_FAILURE, ex);
        }
        return days;
    }

    @Override
    public SprintDay find(String day) throws CardioTechnicalException {
        LOGGER.debug("[DAO] find day '{}' ...", day);
        SprintDay sprintDay = null;
        try {
            sprintDay = jdbcTemplate.queryForObject(SQL_FIND, new Object[] { day }, new SprintDayMapper());
        } catch (EmptyResultDataAccessException notFound) {
            LOGGER.info("No result found with day '{}'", day);
        } catch (Exception ex) {
            throw new CardioTechnicalException(DATABASE_FAILURE, ex);
        }
        return sprintDay;
    }

    @Override
    public SprintDay add(SprintDay sprintDay) throws CardioTechnicalException {
        return insertOrUpdate(sprintDay);
    }

    @Override
    public SprintDay insertOrUpdate(SprintDay sprintDay) throws CardioTechnicalException {
        LOGGER.info("[DAO] insertOrUpdate ...");
        try {
            jdbcTemplate.update(SQL_MERGE, LocalDateUtils.convertToDate(sprintDay.getDay()), sprintDay.getDone());
        } catch (Exception ex) {
            throw new CardioTechnicalException(DATABASE_FAILURE, ex);
        }
        return sprintDay;
    }

    @Override
    public void remove(String day) throws CardioTechnicalException {
        LOGGER.info("[DAO] remove '{}' ...", day);
        try {
            jdbcTemplate.update(SQL_DELETE, day);
        } catch (Exception ex) {
            throw new CardioTechnicalException(DATABASE_FAILURE, ex);
        }
    }

    @Override
    public List<SprintDay> findBetween(String start, String end) throws CardioTechnicalException {
        LOGGER.info("[DAO] findBetween {} and {} ...", start, end);
        List<SprintDay> days = null;
        try {
            String sql = String.format(SQL_FIND_BETWEEN, start, end);
            days = jdbcTemplate.query(sql, new SprintDayMapper());
        } catch (Exception ex) {
            throw new CardioTechnicalException(DATABASE_FAILURE, ex);
        }
        return days;
    }

    @Override
    public SprintDay findLastBetween(String start, String end) throws CardioTechnicalException {
        LOGGER.info("[DAO] findLastBetween {} and {} ...", start, end);
        SprintDay day = null;
        try {
            String sql = String.format(SQL_FIND_LAST_BETWEEN, start, end);
            List<SprintDay> days = jdbcTemplate.query(sql, new SprintDayMapper());
            if (days != null && !days.isEmpty()) {
                day = days.get(0);
            }
        } catch (Exception ex) {
            throw new CardioTechnicalException(DATABASE_FAILURE, ex);
        }
        return day;
    }
}
