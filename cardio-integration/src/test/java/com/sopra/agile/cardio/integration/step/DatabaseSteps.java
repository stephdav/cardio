package com.sopra.agile.cardio.integration.step;

import org.fluentlenium.adapter.cucumber.FluentCucumberTest;
import org.fluentlenium.configuration.ConfigurationProperties;
import org.fluentlenium.configuration.ConfigurationProperties.TriggerMode;
import org.fluentlenium.configuration.FluentConfiguration;
import org.openqa.selenium.WebDriver;

import com.sopra.agile.cardio.integration.utils.CustomWebDriver;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;

@FluentConfiguration(driverLifecycle = ConfigurationProperties.DriverLifecycle.JVM, screenshotMode = TriggerMode.AUTOMATIC_ON_FAIL, screenshotPath = "target/cucumber")
public class DatabaseSteps extends FluentCucumberTest {

    @Override
    public WebDriver newWebDriver() {
        System.setProperty("webdriver.chrome.driver", "/dev/appli/chromedriver/chromedriver.exe");
        return new CustomWebDriver();
    }

    @Before
    public void before(Scenario scenario) {
        super.before(scenario);
    }

    @After
    public void after(Scenario scenario) {
        super.after(scenario);
    }

    @Given("^empty database$")
    public void empty_database() throws Throwable {
        CustomWebDriver cwd = (CustomWebDriver) getDriver();
        cwd.applyScript("data-clear.sql");
    }

    @Given("^database script '(.*)'$")
    public void database_script(String script) throws Throwable {
        CustomWebDriver cwd = (CustomWebDriver) getDriver();
        cwd.applyScript(script);
    }
}
