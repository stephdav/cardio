package com.sopra.agile.cardio.back.dao;

import static com.sopra.agile.cardio.back.dao.DatabaseUtils.initDbConnexion;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.sopra.agile.cardio.common.exception.CardioTechnicalException;
import com.sopra.agile.cardio.common.model.Story;

public class StoryDaoExceptionTest {

    private static JdbcTemplate jdbc;

    private StoryDao dao;

    @BeforeClass
    public static void classSetUp() throws Exception {
        jdbc = initDbConnexion("drop-schema.sql");
    }

    @Before
    public void setUp() throws Exception {
        dao = new StoryDaoImpl(jdbc);
    }

    @Test(expected = CardioTechnicalException.class)
    public void testAll() throws CardioTechnicalException {
        dao.all();
    }

    @Test(expected = CardioTechnicalException.class)
    public void testFindStory() throws CardioTechnicalException {
        dao.find(0);
    }

    @Test(expected = CardioTechnicalException.class)
    public void testAddStory() throws CardioTechnicalException {
        dao.add(new Story());
    }

    @Test(expected = CardioTechnicalException.class)
    public void testRemoveStory() throws CardioTechnicalException {
        dao.remove(0);
    }

}
