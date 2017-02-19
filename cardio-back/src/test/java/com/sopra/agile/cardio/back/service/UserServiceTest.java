package com.sopra.agile.cardio.back.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import com.sopra.agile.cardio.back.dao.UserDao;
import com.sopra.agile.cardio.common.exception.CardioFunctionalException;
import com.sopra.agile.cardio.common.exception.CardioTechnicalException;
import com.sopra.agile.cardio.common.model.User;

public class UserServiceTest {

    private UserService svc;
    private UserDao dao;

    @Before
    public void init() throws CardioTechnicalException {
        svc = new UserServiceImpl();

        dao = mock(UserDao.class);
        ReflectionTestUtils.setField(svc, "userDao", dao);

        User[] aUsers = new User[3];
        for (int idx = 1; idx <= 3; idx++) {
            aUsers[idx - 1] = new User(idx, "LOGIN" + idx, "FIRSTNAME" + idx, "LASTNAME" + idx);
        }
        List<User> users = Arrays.asList(aUsers);
        when(dao.all()).thenReturn(users);
        when(dao.find(1)).thenReturn(aUsers[0]);
        when(dao.find(2)).thenReturn(aUsers[1]);
        when(dao.find(0)).thenReturn(null);

        User newUser = new User(999, "TST", "TST", "TST");
        when(dao.add(any(User.class))).thenReturn(newUser);

    }

    @Test
    public void testAll() throws CardioTechnicalException, CardioFunctionalException {
        List<User> users = svc.all();
        assertNotNull(users);
        assertEquals(3, users.size());
    }

    @Test
    public void testFindUser() throws CardioTechnicalException, CardioFunctionalException {
        // User must be found
        User usr01 = svc.find("1");
        assertNotNull(usr01);
        assertEquals("LOGIN1", usr01.getLogin());

        // User not found
        User usrUNK = svc.find("0");
        assertNull(usrUNK);
    }

    @Test
    public void testAddUser() throws CardioTechnicalException, CardioFunctionalException {
        User usr = svc.add(new User(0, "TST", "TST", "TST"));
        assertNotNull(usr);
        assertEquals("TST", usr.getLogin());
    }

    @Test
    public void testRemoveUser() throws CardioTechnicalException {
        svc.remove("2");
        verify(dao).remove(2);
    }

}
