package com.sopra.agile.cardio.app;

import org.junit.rules.ExternalResource;

import spark.Spark;

public class ServerRule extends ExternalResource {

    protected void before() {
        System.setProperty("webdriver.chrome.driver", "/dev/appli/chromedriver/chromedriver.exe");
        String[] args = {};
        App.main(args);
    }

    protected void after() {
        Spark.stop();
    }
}