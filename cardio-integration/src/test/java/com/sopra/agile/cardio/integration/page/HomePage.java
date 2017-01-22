package com.sopra.agile.cardio.integration.page;

import org.junit.Assert;

public class HomePage extends BasePage {

    @Override
    public String getUrl() {
        return ROOT_URL;
    }

    @Override
    public void isAt() {
        super.isAt();
        Assert.assertEquals("SELENIUM", $("#project-name").text());
    }

}