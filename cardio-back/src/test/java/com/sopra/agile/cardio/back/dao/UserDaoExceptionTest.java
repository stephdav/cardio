package com.sopra.agile.cardio.back.dao;

import static com.sopra.agile.cardio.back.dao.DatabaseUtils.initDbConnexion;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.sopra.agile.cardio.common.exception.CardioTechnicalException;
import com.sopra.agile.cardio.common.model.User;

public class UserDaoExceptionTest {

    private static JdbcTemplate jdbc;

    private UserDao dao;

    @BeforeClass
    public static void classSetUp() throws Exception {
        jdbc = initDbConnexion("drop-schema.sql");
    }

    @Before
    public void setUp() throws Exception {
        dao = new UserDaoImpl(jdbc);
    }

    @Test(expected = CardioTechnicalException.class)
    public void testAll() throws CardioTechnicalException {
        dao.all();
    }

    @Test(expected = CardioTechnicalException.class)
    public void testFindUser() throws CardioTechnicalException {
        dao.find(0);
    }

    @Test(expected = CardioTechnicalException.class)
    public void testAddUser() throws CardioTechnicalException {
        dao.add(new User());
    }

    @Test(expected = CardioTechnicalException.class)
    public void testFindByLogin() throws CardioTechnicalException {
        dao.findByLogin("XYZ");
    }

    @Test(expected = CardioTechnicalException.class)
    public void testRemoveUser() throws CardioTechnicalException {
        dao.remove(0);
    }

}
