package com.sopra.agile.cardio.back.dao;

import static com.sopra.agile.cardio.back.dao.DatabaseUtils.initDbConnexion;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import com.sopra.agile.cardio.back.utils.converter.Converter;
import com.sopra.agile.cardio.common.exception.CardioTechnicalException;
import com.sopra.agile.cardio.common.model.Sprint;

public class SprintDaoExceptionTest {

    private static JdbcTemplate jdbc;

    private SprintDao dao;

    @BeforeClass
    public static void classSetUp() throws Exception {
        jdbc = initDbConnexion("drop-schema.sql");
    }

    @Before
    public void setUp() throws Exception {
        dao = new SprintDaoImpl(jdbc);
        ReflectionTestUtils.setField(dao, "mapper", new Converter());
    }

    @Test(expected = CardioTechnicalException.class)
    public void testAll() throws CardioTechnicalException {
        dao.all();
    }

    @Test(expected = CardioTechnicalException.class)
    public void testFindSprint() throws CardioTechnicalException {
        dao.find("0");
    }

    @Test(expected = CardioTechnicalException.class)
    public void testAddSprint() throws CardioTechnicalException {
        dao.add(new Sprint());
    }

    @Test(expected = CardioTechnicalException.class)
    public void testRemoveSprint() throws CardioTechnicalException {
        dao.remove("0");
    }

    @Test(expected = CardioTechnicalException.class)
    public void testFindByName() throws CardioTechnicalException {
        dao.findByName("name");
    }

    @Test(expected = CardioTechnicalException.class)
    public void testFindByDay() throws CardioTechnicalException {
        dao.findByDay("day");
    }

    @Test(expected = CardioTechnicalException.class)
    public void testUpdate() throws CardioTechnicalException {
        dao.update(new Sprint());
    }

    @Test(expected = CardioTechnicalException.class)
    public void testAllCompleted() throws CardioTechnicalException {
        dao.allCompleted();
    }

    @Test(expected = CardioTechnicalException.class)
    public void testOverlaping() throws CardioTechnicalException {
        dao.overlaping(new Sprint());
    }

}
