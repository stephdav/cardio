package com.sopra.agile.cardio.app;

import org.fluentlenium.adapter.junit.FluentTest;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

public class AppTest extends FluentTest {

    @Override
    public String getWebDriver() {
        return "chrome";
    }

    @ClassRule
    public static ServerRule server = new ServerRule();

    @Rule
    public ScreenshotTestRule screenshot = new ScreenshotTestRule(this);

    @Test
    public void rootTest() {
        window().maximize();
        goTo("http://localhost:4567/");
        Assert.assertEquals("cardio", window().title());
        Assert.assertEquals("SELENIUM", $("#project-name").text());
    }

    @Test
    public void emptyDashboardTest() {
        window().maximize();
        goTo("http://localhost:4567/");
        Assert.assertEquals("no pending sprint", $("#sprint-name").text());
        Assert.assertEquals("", $("#sprint-goal").text());
        Assert.assertEquals("", $("#from-day").text());
        Assert.assertEquals("", $("#from-month").text());
        Assert.assertEquals("", $("#to-day").text());
        Assert.assertEquals("", $("#to-month").text());
    }
}