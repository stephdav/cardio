package com.sopra.agile.cardio.back.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.sopra.agile.cardio.common.model.SprintDay;
import com.sopra.agile.cardio.common.utils.LocalDateUtils;

@Service
public class SprintDayDaoImpl implements SprintDayDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(SprintDayDaoImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public SprintDayDaoImpl() {
        // Empty constructor
    }

    public SprintDayDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<SprintDay> all() {
        LOGGER.info("all ...");
        String sql = "select * from SPRINT_DAYS";
        return jdbcTemplate.query(sql, new SprintDayMapper());
    }

    @Override
    public SprintDay find(String day) {
        LOGGER.info("find '{}' ...", day);
        String sql = "select * from SPRINT_DAYS where day = ?";
        SprintDay sprintDay = null;
        try {
            sprintDay = jdbcTemplate.queryForObject(sql, new Object[] { day }, new SprintDayMapper());
        } catch (EmptyResultDataAccessException notFound) {
            LOGGER.info("No result found with day '{}'", day);
        }
        return sprintDay;
    }

    @Override
    public SprintDay add(SprintDay sprintDay) {
        LOGGER.info("add ...");
        String sql = "insert into SPRINT_DAYS(DAY, COMMITMENT_LEFT) values (?, ?)";
        jdbcTemplate.update(sql, LocalDateUtils.convertToDate(sprintDay.getDay()), sprintDay.getCommitmentLeft());
        return sprintDay;
    }

    @Override
    public void remove(String day) {
        LOGGER.info("remove '{}' ...", day);
        String SQL = "delete from SPRINT_DAYS where day = ?";
        jdbcTemplate.update(SQL, day);
    }

    @Override
    public List<SprintDay> findBetween(String start, String end) {
        LOGGER.info("all ...");
        String sql = String.format("select * from SPRINT_DAYS where DATE '%s' <= DAY AND DAY <= DATE '%s'", start, end);
        List<SprintDay> days = jdbcTemplate.query(sql, new SprintDayMapper());
        return days;
    }
}
