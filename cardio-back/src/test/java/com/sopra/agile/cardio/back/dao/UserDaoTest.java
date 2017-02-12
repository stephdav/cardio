package com.sopra.agile.cardio.back.dao;

import static com.sopra.agile.cardio.back.dao.DatabaseUtils.count;
import static com.sopra.agile.cardio.back.dao.DatabaseUtils.initDbConnexion;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.sopra.agile.cardio.common.exception.CardioTechnicalException;
import com.sopra.agile.cardio.common.model.User;

public class UserDaoTest {

    private static JdbcTemplate jdbc;

    private UserDao dao;

    private static final String USERS = "USERS";

    @BeforeClass
    public static void classSetUp() throws Exception {
        jdbc = initDbConnexion("insert-dataTest.sql");
    }

    @Before
    public void setUp() throws Exception {
        dao = new UserDaoImpl(jdbc);
    }

    @Test
    public void testConstructor() {
        assertNotNull(dao);
    }

    @Test
    public void testAll() throws CardioTechnicalException {
        List<User> users = dao.all();
        assertNotNull(users);
        assertEquals(count(jdbc, USERS), users.size());
    }

    @Test
    public void testFindUser() throws CardioTechnicalException {
        // User must be found
        User usr = dao.find("USR-1");
        assertNotNull(usr);
        assertEquals("SDD", usr.getLogin());
        assertEquals("Stephane", usr.getFirstname());
        assertEquals("David", usr.getLastname());

        // User not found
        User usrUNK = dao.find("UNK");
        assertNull(usrUNK);
    }

    @Test
    public void testAddUser() throws CardioTechnicalException {
        int count = count(jdbc, USERS);
        dao.add(new User(null, "TST", "TST", "TST"));
        assertEquals(count + 1, count(jdbc, USERS));
    }

    @Test
    public void testFindByLogin() throws CardioTechnicalException {
        // User must be found
        User usr = dao.findByLogin("TBO");
        assertNotNull(usr);
        assertEquals("TBO", usr.getLogin());
        assertEquals("Tristan", usr.getFirstname());
        assertEquals("Bouju", usr.getLastname());

        // User not found
        User usrUNK = dao.findByLogin("UNK");
        assertNull(usrUNK);
    }

    @Test
    public void testRemoveUser() throws CardioTechnicalException {
        int count = count(jdbc, USERS);
        dao.remove("TO_BE_DELETED");
        assertEquals(count - 1, count(jdbc, USERS));
    }

}
