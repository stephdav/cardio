package com.sopra.agile.cardio.integration;

import org.apache.http.annotation.NotThreadSafe;
import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:features", plugin = { "pretty", "html:target/cucumber",
        "json:target/cucumber.json" })
@NotThreadSafe
public class TestRunner {

}