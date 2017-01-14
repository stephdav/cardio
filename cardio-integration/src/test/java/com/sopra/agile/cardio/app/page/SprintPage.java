package com.sopra.agile.cardio.app.page;

import static org.fluentlenium.core.filter.FilterConstructor.withText;

import org.junit.Assert;

public class SprintPage extends BasePage {
    @Override
    public String getUrl() {
        return ROOT_URL + "sprint/{id}";
    }

    @Override
    public void isAt() {
        super.isAt();
        Assert.assertTrue($(".panel-heading", withText("sprint properties")).present());
        Assert.assertTrue($(".panel-heading", withText("sprint data")).present());
        Assert.assertTrue($(".panel-heading", withText("sprint burndown")).present());
    }

}