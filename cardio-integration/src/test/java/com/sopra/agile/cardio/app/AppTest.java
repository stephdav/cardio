package com.sopra.agile.cardio.app;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

import com.sopra.agile.cardio.app.utils.ServerRule;

public class AppTest extends AbstractBaseTest {

    @ClassRule
    public static ServerRule server = new ServerRule();

    @Test
    public void rootTest() {
        homePage.go();
        await().atMost(5, TimeUnit.SECONDS).untilPage(homePage).isAt();

        Assert.assertEquals("no pending sprint", $("#sprint-name").text());
        Assert.assertEquals("", $("#sprint-goal").text());
        Assert.assertEquals("", $("#from-day").text());
        Assert.assertEquals("", $("#from-month").text());
        Assert.assertEquals("", $("#to-day").text());
        Assert.assertEquals("", $("#to-month").text());
    }
}