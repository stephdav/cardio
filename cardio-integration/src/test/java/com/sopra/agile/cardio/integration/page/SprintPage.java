package com.sopra.agile.cardio.integration.page;

import static org.fluentlenium.core.filter.FilterConstructor.withText;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SprintPage extends BasePage {

    @Override
    public String getUrl() {
        return ROOT_URL + "sprint/{id}";
    }

    @Override
    public void isAt() {
        super.isAt();
        assertTrue($(".panel-heading", withText("sprint properties")).present());
        assertTrue($(".panel-heading", withText("sprint data")).present());
        assertTrue($(".panel-heading", withText("sprint burndown")).present());
    }

    public void testSprintProperties(String from, String to, String name, String goal, String commitment) {
        if (from != null) {
            assertEquals(from, $("#sprintStartDate").value());
        }
        if (to != null) {
            assertEquals(to, $("#sprintEndDate").value());
        }
        if (name != null) {
            assertEquals(name, $("#sprintName").value());
        }
        if (goal != null) {
            assertEquals(goal, $("#sprintGoal").value());
        }
        if (commitment != null) {
            assertEquals(commitment, $("#sprintCommitment").value());
        }
    }

}