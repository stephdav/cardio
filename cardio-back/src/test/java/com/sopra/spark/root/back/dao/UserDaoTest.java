package com.sopra.spark.root.back.dao;

import static com.sopra.spark.root.back.dao.DatabaseUtils.count;
import static com.sopra.spark.root.back.dao.DatabaseUtils.initDbConnexion;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.sopra.agile.cardio.dao.UserDao;
import com.sopra.agile.cardio.dao.UserDaoImpl;
import com.sopra.agile.cardio.model.User;

public class UserDaoTest {

    private static JdbcTemplate jdbc;

    private UserDao dao;

    @BeforeClass
    public static void classSetUp() throws Exception {
        jdbc = initDbConnexion("insert-dataTest.sql");
    }

    @Before
    public void setUp() throws Exception {
        dao = new UserDaoImpl(jdbc);
    }

    @Test
    public void testAll() {
        List<User> users = dao.all();
        assertNotNull(users);
        assertEquals(count(jdbc, "USERS"), users.size());
    }

    @Test
    public void testFindUser() {
        // User must be found
        User usr01 = dao.find("01");
        assertNotNull(usr01);
        assertEquals("SDD", usr01.getLogin());

        // User not found
        User usrUNK = dao.find("UNK");
        assertNull(usrUNK);
    }

    @Test
    public void testAddUser() {
        int count = count(jdbc, "USERS");
        dao.add(new User(null, "TST", "TST", "TST"));
        assertEquals(count + 1, count(jdbc, "USERS"));
    }

    @Test
    public void testRemoveUser() {
        int count = count(jdbc, "USERS");
        dao.remove("TO_BE_DELETED");
        assertEquals(count - 1, count(jdbc, "USERS"));
    }

}
