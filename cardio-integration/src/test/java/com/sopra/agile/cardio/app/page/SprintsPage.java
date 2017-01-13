package com.sopra.agile.cardio.app.page;

import org.junit.Assert;

public class SprintsPage extends BasePage {
    @Override
    public String getUrl() {
        return ROOT_URL + "sprints";
    }

    @Override
    public void isAt() {
        super.isAt();
        Assert.assertEquals("add sprint", $("#addSprint").text());
    }
}