package com.sopra.agile.cardio.app.utils;

import java.util.ArrayList;
import java.util.List;

import org.junit.rules.ExternalResource;

import com.sopra.agile.cardio.app.App;

import spark.Spark;

public class ServerRule extends ExternalResource {

    private List<String> params;

    public ServerRule(String... args) {
        params = new ArrayList<String>();
        params.add("reset");
        for (String arg : args) {
            params.add(arg);
        }
    }

    protected void before() {
        System.setProperty("webdriver.chrome.driver", "/dev/appli/chromedriver/chromedriver.exe");
        String[] args = params.toArray(new String[params.size()]);
        App.main(args);
    }

    protected void after() {
        Spark.stop();
    }
}