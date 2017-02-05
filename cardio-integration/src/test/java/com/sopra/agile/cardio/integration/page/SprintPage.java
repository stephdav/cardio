package com.sopra.agile.cardio.integration.page;

import static org.fluentlenium.core.filter.FilterConstructor.withText;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

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

    public void testSprintData(List<String> expectedValues) {
        List<String> values = $("#measures input").values();
        assertEquals(expectedValues.size(), values.size());
        int idx = 0;
        for (String val : expectedValues) {
            assertEquals(val, values.get(idx++));
        }
    }

    public void updateName(String name) {
        $("#sprintName").fill().with(name);
    }

    public void updateCommitment(String commitment) {
        $("#sprintCommitment").fill().with(commitment);
    }

    public void validatePropertiesUpdate() {
        $("#updateSprintProperties").click();
    }

    public void updateData(int day, String done) {
        $("#measures input").get(day - 1).fill().with(done);
    }

    public void validateDataUpdate() {
        $("#updateSprintData").click();
    }

}