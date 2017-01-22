package com.sopra.agile.cardio.app.selenium;

import org.junit.ClassRule;
import org.junit.Test;

import com.sopra.agile.cardio.app.utils.ServerRule;

public class SprintPageTest extends AbstractBaseTest {

    @ClassRule
    public static ServerRule server = new ServerRule("data-2016-01.sql");

    @Test
    public void testSprintUpdate() {
        sprintsPage.go();
        sprintsPage.isAt();

        sprintsPage.sprintListContainsSprint("2016-01-04", "2016-01-15", "1", "sprint 1", "25", "22");

        sprintsPage.clickOnSprint("1");
        sprintPage.isAt();

        sprintPage.testSprintProperties("2016-01-04", "2016-01-15", "1", "sprint 1", "25");
    }

}