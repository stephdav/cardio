package com.sopra.agile.cardio.app.cucumber.utils;

import org.fluentlenium.adapter.cucumber.FluentCucumberTest;

public class CucumberScreenshot {

    public static void captureScreenshot(FluentCucumberTest testClass, String filename) {
        testClass.takeScreenShot(filename + ".png");
    }

}
