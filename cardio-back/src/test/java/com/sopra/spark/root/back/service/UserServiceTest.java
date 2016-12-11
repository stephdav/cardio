package com.sopra.spark.root.back.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.sopra.agile.cardio.dao.UserDao;
import com.sopra.agile.cardio.model.User;
import com.sopra.agile.cardio.service.UserService;
import com.sopra.agile.cardio.service.UserServiceImpl;

public class UserServiceTest {

    private UserService svc;
    private UserDao dao;

    @Before
    public void init() throws SQLException {
        dao = mock(UserDao.class);
        svc = new UserServiceImpl(dao);

        User[] aUsers = new User[3];
        for (int idx = 0; idx < 3; idx++) {
            aUsers[idx] = new User("USR-" + idx, "LOGIN" + idx, "FIRSTNAME" + idx, "LASTNAME" + idx);
        }
        List<User> users = Arrays.asList(aUsers);
        when(dao.all()).thenReturn(users);
        when(dao.find("USR-0")).thenReturn(aUsers[0]);
        when(dao.find("UNK")).thenReturn(null);

        User newUser = new User("TST", "TST", "TST", "TST");
        when(dao.add(any(User.class))).thenReturn(newUser);

    }

    @Test
    public void testAll() {
        List<User> users = svc.all();
        assertNotNull(users);
        assertEquals(3, users.size());
    }

    @Test
    public void testFindUser() {
        // User must be found
        User usr01 = svc.find("USR-0");
        assertNotNull(usr01);
        assertEquals("LOGIN0", usr01.getLogin());

        // User not found
        User usrUNK = svc.find("UNK");
        assertNull(usrUNK);
    }

    @Test
    public void testAddUser() {
        User usr = svc.add(new User(null, "TST", "TST", "TST"));
        assertNotNull(usr);
        assertEquals("TST", usr.getLogin());
    }

    @Test
    public void testRemoveUser() {
        svc.remove("USR-0");
        verify(dao).remove("USR-0");
    }

}
