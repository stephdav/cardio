package com.sopra.agile.cardio.integration.page;

import static org.fluentlenium.core.filter.FilterConstructor.withText;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SprintPlanningPage extends BasePage {

    @Override
    public String getUrl() {
        return ROOT_URL + "sprintPlanning";
    }

    @Override
    public void isAt() {
        super.isAt();
        assertTrue($(".panel-heading", withText("velocity")).present());
    }

    public void testTrendValues(String bad, String avg, String good) {
        if (bad != null) {
            // await().atMost(3, TimeUnit.SECONDS).until($("#worst-sprints",
            // withText(bad))).displayed();
            assertEquals(bad, $("#worst-sprints").text());
        }
        if (avg != null) {
            assertEquals(avg, $("#average-sprints").text());
        }
        if (good != null) {
            assertEquals(good, $("#best-sprints").text());
        }
    }

}