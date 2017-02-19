package com.sopra.agile.cardio.back.dao;

import static com.sopra.agile.cardio.back.dao.DatabaseUtils.initDbConnexion;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.sopra.agile.cardio.common.exception.CardioTechnicalException;
import com.sopra.agile.cardio.common.model.SprintDay;

public class SprintDayDaoExceptionTest {

    private static JdbcTemplate jdbc;

    private SprintDayDao dao;

    @BeforeClass
    public static void classSetUp() throws Exception {
        jdbc = initDbConnexion("drop-schema.sql");
    }

    @Before
    public void setUp() throws Exception {
        dao = new SprintDayDaoImpl(jdbc);
    }

    @Test(expected = CardioTechnicalException.class)
    public void testAll() throws CardioTechnicalException {
        dao.all();
    }

    @Test(expected = CardioTechnicalException.class)
    public void testFindSprintDay() throws CardioTechnicalException {
        dao.find("0");
    }

    @Test(expected = CardioTechnicalException.class)
    public void testAddSprintDay() throws CardioTechnicalException {
        dao.add(new SprintDay());
    }

    @Test(expected = CardioTechnicalException.class)
    public void testRemoveSprintDay() throws CardioTechnicalException {
        dao.remove("0");
    }

    @Test(expected = CardioTechnicalException.class)
    public void testFindBetween() throws CardioTechnicalException {
        dao.findBetween("start", "end");
    }

    @Test(expected = CardioTechnicalException.class)
    public void testFindLastBetween() throws CardioTechnicalException {
        dao.findLastBetween("start", "end");
    }

    @Test(expected = CardioTechnicalException.class)
    public void testInsertOrUpdate() throws CardioTechnicalException {
        dao.insertOrUpdate(new SprintDay());
    }

}
