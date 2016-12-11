package com.sopra.agile.cardio.common.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class AppProfileTest {

    @Test
    public void testIsAuthorized() {
        assertEquals(true, AppProfile.isAuthorized("cleardb"));
        assertEquals(true, AppProfile.isAuthorized("populatedb"));
    }

    @Test
    public void testIsNotAuthorized() {
        assertEquals(false, AppProfile.isAuthorized(null));
        assertEquals(false, AppProfile.isAuthorized(""));
        assertEquals(false, AppProfile.isAuthorized("inmemory"));
    }

}
