package com.sopra.agile.cardio.common.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class UserTest {

    @Test
    public void testDefaultConstructor() {
        User usr = new User();
        assertObject(usr, null, null, null, null);
    }

    @Test
    public void testConstructor() {
        User usr = new User("id", "login");
        assertObject(usr, "id", "login", null, null);
    }

    @Test
    public void testFullConstructor() {
        User usr = new User("id", "login", "firstanme", "lastname");
        assertObject(usr, "id", "login", "firstanme", "lastname");
    }

    @Test
    public void testFields() {
        User usr = new User();
        usr.setId("id");
        usr.setLogin("login");
        usr.setFirstname("firstname");
        usr.setLastname("lastname");
        assertObject(usr, "id", "login", "firstname", "lastname");
    }

    private void assertObject(User usr, final String id, final String login, final String firstname,
            final String lastname) {
        if (id == null) {
            assertNull(usr.getId());
        } else {
            assertEquals(id, usr.getId());
        }
        if (login == null) {
            assertNull(usr.getLogin());
        } else {
            assertEquals(login, usr.getLogin());
        }
        if (firstname == null) {
            assertNull(usr.getFirstname());
        } else {
            assertEquals(firstname, usr.getFirstname());
        }
        if (lastname == null) {
            assertNull(usr.getLastname());
        } else {
            assertEquals(lastname, usr.getLastname());
        }
    }

}
