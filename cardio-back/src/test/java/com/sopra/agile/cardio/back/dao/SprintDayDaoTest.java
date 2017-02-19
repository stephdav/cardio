package com.sopra.agile.cardio.back.dao;

import static com.sopra.agile.cardio.back.dao.DatabaseUtils.count;
import static com.sopra.agile.cardio.back.dao.DatabaseUtils.initDbConnexion;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.sopra.agile.cardio.common.exception.CardioTechnicalException;
import com.sopra.agile.cardio.common.model.SprintDay;

public class SprintDayDaoTest {

    private static JdbcTemplate jdbc;

    private SprintDayDao dao;

    private static final String SPRINT_DAYS = "SPRINT_DAYS";

    @BeforeClass
    public static void classSetUp() throws Exception {
        jdbc = initDbConnexion("insert-dataTest.sql");
    }

    @Before
    public void setUp() throws Exception {
        dao = new SprintDayDaoImpl(jdbc);
    }

    @Test
    public void testConstructor() {
        assertNotNull(dao);
    }

    @Test
    public void testAll() throws CardioTechnicalException {
        List<SprintDay> days = dao.all();
        assertNotNull(days);
        assertEquals(count(jdbc, SPRINT_DAYS), days.size());
    }

    @Test
    public void testFindSprintDay() throws CardioTechnicalException {
        // Sprint must be found
        SprintDay day = dao.find("2016-01-05");
        assertNotNull(day);
        assertEquals(new LocalDate("2016-01-05"), day.getDay());
        assertEquals(5, day.getDone());

        // Sprint not found
        SprintDay dayOff = dao.find("2016-01-02");
        assertNull(dayOff);
    }

    @Test
    public void testAddSprintDay() throws CardioTechnicalException {
        int count = count(jdbc, SPRINT_DAYS);
        dao.add(new SprintDay(new LocalDate("2016-02-14"), 2));
        assertEquals(count + 1, count(jdbc, SPRINT_DAYS));
    }

    @Test
    public void testUpdateSprintDay() throws CardioTechnicalException {
        int count = count(jdbc, SPRINT_DAYS);
        dao.insertOrUpdate(new SprintDay(new LocalDate("2016-07-14"), 789));
        assertEquals(count, count(jdbc, SPRINT_DAYS));
    }

    @Test
    public void testRemoveSprintDay() throws CardioTechnicalException {
        int count = count(jdbc, SPRINT_DAYS);
        dao.remove("2015-12-31");
        assertEquals(count - 1, count(jdbc, SPRINT_DAYS));
    }

    @Test
    public void testFindBetween() throws CardioTechnicalException {
        List<SprintDay> days = dao.findBetween("2016-01-01", "2016-01-31");
        assertNotNull(days);
        assertEquals(6, days.size());
    }

    @Test
    public void testFindLastBetween() throws CardioTechnicalException {
        SprintDay day = dao.findLastBetween("2016-01-01", "2016-01-10");
        assertNotNull(day);
        assertEquals(new LocalDate("2016-01-08"), day.getDay());

        day = dao.findLastBetween("2010-01-01", "2010-12-31");
        assertNull(day);

    }

    @Test
    public void testFindLastBetween_noResult() throws CardioTechnicalException {
        SprintDay day = dao.findLastBetween("2016-03-01", "2016-03-10");
        assertNull(day);
    }
}
