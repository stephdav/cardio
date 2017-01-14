package com.sopra.agile.cardio.app.page;

import static org.fluentlenium.core.filter.FilterConstructor.withText;

import java.util.concurrent.TimeUnit;

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
        Assert.assertTrue($(".panel-heading", withText("sprints")).present());
        Assert.assertTrue($(".panel-heading", withText("new sprint")).present());
        Assert.assertEquals("add sprint", $("#addSprint").text());
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
        await().atMost(5, TimeUnit.SECONDS).until($("#errors", withText(message))).present();
    }

    public void sprintListIsEmpty() {
        Assert.assertEquals(1, $("#sprints-table>tbody>tr").size());
        Assert.assertTrue($("#sprints-table>tbody>tr.no-records-found").present());
        Assert.assertEquals("", $(".pagination-info").text());
    }

    public void sprintListContains(int page, int total) {
        Assert.assertEquals(page, $("#sprints-table>tbody>tr").size());
        Assert.assertFalse($("#sprints-table>tbody>tr.no-records-found").present());
        Assert.assertEquals("Showing 1 to " + page + " of " + total + " rows", $(".pagination-info").text());
    }

    public void clickOnSprint(String name) {
        $("#sprints-table>tbody>tr>td", withText(name)).click();
    }
}