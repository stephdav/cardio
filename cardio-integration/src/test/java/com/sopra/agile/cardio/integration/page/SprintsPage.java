package com.sopra.agile.cardio.integration.page;

import static org.fluentlenium.core.filter.FilterConstructor.withText;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.fluentlenium.core.domain.FluentWebElement;
import org.junit.Assert;
import org.openqa.selenium.By;

public class SprintsPage extends BasePage {

    @Override
    public String getUrl() {
        return ROOT_URL + "sprints";
    }

    @Override
    public void isAt() {
        super.isAt();
        assertTrue($(".panel-heading", withText("sprints")).present());
        assertTrue($(".panel-heading", withText("new sprint")).present());
        assertEquals("add sprint", $("#addSprint").text());
        assertTrue($("#sprints-table>tbody").present());
    }

    public void createSprint(String name, String startDate, String endDate) {
        if (name != null) {
            $("#sprintName").fill().with(name);
        }
        if (startDate != null) {
            getDriver().findElement(By.id("sprintStartDate")).sendKeys(startDate);
        }
        if (endDate != null) {
            getDriver().findElement(By.id("sprintEndDate")).sendKeys(endDate);
        }
        $("#addSprint").click();
    }

    public void errorTextIs(String message) {
        if (message != null && !message.isEmpty()) {
            await().atMost(3, TimeUnit.SECONDS).until($("#errors", withText(message))).displayed();
        } else {
            await().atMost(3, TimeUnit.SECONDS).until($("#errors", withText(message))).not().displayed();
        }

    }

    public void sprintListIsEmpty() {
        assertEquals(1, $("#sprints-table>tbody>tr").size());
        assertTrue($("#sprints-table>tbody>tr.no-records-found").present());
        assertEquals("", $(".pagination-info").text());
    }

    public void sprintListContains(int page, int total) {
        Assert.assertEquals(page, $("#sprints-table>tbody>tr").size());
        await().atMost(3, TimeUnit.SECONDS).until($("#sprints-table>tbody>tr.no-records-found")).not().present();
        // Assert.assertFalse($("#sprints-table>tbody>tr.no-records-found").present());
        Assert.assertEquals("Showing 1 to " + page + " of " + total + " rows", $(".pagination-info").text());
    }

    public void sprintListContainsSprint(String from, String to, String name, String goal, String commitment,
            String velocity) {

        boolean found = false;
        List<String> tds = null;
        for (FluentWebElement elt : $("#sprints-table>tbody>tr")) {
            tds = elt.$("td").texts();
            if (from != null && !from.equals(tds.get(0))) {
                continue;
            }
            if (to != null && !to.equals(tds.get(1))) {
                continue;
            }
            if (name != null && !name.equals(tds.get(2))) {
                continue;
            }
            if (goal != null && !goal.equals(tds.get(3))) {
                continue;
            }
            if (commitment != null && !commitment.equals(tds.get(4))) {
                continue;
            }
            if (velocity != null && !velocity.equals(tds.get(5))) {
                continue;
            }
            found = true;
            break;
        }
        Assert.assertTrue(found);

    }

    public void clickOnSprint(String name) {
        $("#sprints-table>tbody>tr>td", withText(name)).click();
    }
}