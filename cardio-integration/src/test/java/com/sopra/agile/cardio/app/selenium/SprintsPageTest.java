package com.sopra.agile.cardio.app.selenium;

import org.junit.ClassRule;
import org.junit.Test;

import com.sopra.agile.cardio.app.utils.ServerRule;

public class SprintsPageTest extends AbstractBaseTest {

    @ClassRule
    public static ServerRule server = new ServerRule();

    @Test
    public void sprintsTest() {
        homePage.go();
        homePage.goToSprints();
        sprintsPage.isAt();
    }

    @Test
    public void sprintCreationErrors() {
        sprintsPage.go();
        sprintsPage.isAt();

        sprintsPage.sprintListIsEmpty();
        sprintsPage.errorTextIs("");

        sprintsPage.createSprint(null, null, null);
        sprintsPage.errorTextIs("name is mandatory");

        sprintsPage.createSprint("1", null, null);
        sprintsPage.errorTextIs("start date is mandatory");

        sprintsPage.createSprint("1", "01/01/2015", null);
        sprintsPage.errorTextIs("end date is mandatory");

        sprintsPage.createSprint("1", "01/12/2015", "31/12/2015");
        sprintsPage.errorTextIs("");

        sprintsPage.sprintListContains(1, 1);

        sprintsPage.createSprint("1", "01/01/2016", "31/01/2016");
        sprintsPage.errorTextIs("sprint with same name already exists");

        sprintsPage.createSprint("2", "15/12/2015", "31/01/2016");
        sprintsPage.errorTextIs("sprint overlapping");

        sprintsPage.createSprint("2", "01/01/2016", "31/01/2016");
        sprintsPage.errorTextIs("");

        sprintsPage.sprintListContains(2, 2);

        sprintsPage.clickOnSprint("1");

        sprintPage.isAt();
    }

}