package com.sopra.agile.cardio.integration.utils;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sopra.agile.cardio.app.App;
import com.sopra.agile.cardio.back.utils.DatabaseSetup;

import spark.Spark;

public class CustomWebDriver extends ChromeDriver {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomWebDriver.class);

    private App app;

    public CustomWebDriver(Capabilities capabilities) {
        super(capabilities);
    }

    public CustomWebDriver() {
        super();

    }

    @Override
    protected void startClient() {
        LOGGER.debug("Start server");

        DatabaseSetup.addScript("reset");

        app = new App();
        app.start();
        app.registerStop();

        while (!RestClient.isAPIStarted()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }

        LOGGER.debug("Server started");

        super.startClient();
    }

    @Override
    protected void stopClient() {

        LOGGER.debug("Stop server");

        Spark.stop();
        while (RestClient.isAPIStarted()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }

        LOGGER.debug("Server stopped");

        super.stopClient();
    }

    public void applyScript(String script) {
        if (app == null) {
            LOGGER.warn("Server is not started");
        } else {
            app.applyScript(script);
        }
    }

}
