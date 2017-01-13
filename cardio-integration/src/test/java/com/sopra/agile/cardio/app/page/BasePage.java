package com.sopra.agile.cardio.app.page;

import org.fluentlenium.core.FluentPage;
import org.junit.Assert;

public class BasePage extends FluentPage {

    protected static final String ROOT_URL = "http://localhost:5678/ui/";

    @Override
    public void isAt() {
        Assert.assertEquals("cardio", window().title());
    }
}