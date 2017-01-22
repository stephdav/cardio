package com.sopra.agile.cardio.app.cucumber.utils;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sopra.agile.cardio.app.App;

import spark.Spark;

public class CustomWebDriver extends ChromeDriver {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomWebDriver.class);

    public CustomWebDriver(Capabilities capabilities) {
        super(capabilities);
    }

    public CustomWebDriver() {
        super();
    }

    @Override
    protected void startClient() {
        LOGGER.debug("Start server");
        List<String> params = new ArrayList<String>();
        params.add("reset");
        App.main(params.toArray(new String[params.size()]));
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

}
