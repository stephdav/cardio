package com.sopra.agile.cardio.app;

import org.junit.Test;

public class SprintTest extends AbstractBaseTest {

    @Test
    public void sprintsTest() {
        homePage.go();
        homePage.goToSprints();
        sprintsPage.isAt();
    }
}