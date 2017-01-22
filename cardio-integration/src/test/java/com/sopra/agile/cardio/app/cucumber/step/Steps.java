package com.sopra.agile.cardio.app.cucumber.step;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.fluentlenium.adapter.cucumber.FluentCucumberTest;
import org.fluentlenium.configuration.ConfigurationProperties;
import org.fluentlenium.configuration.FluentConfiguration;
import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.annotation.Page;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sopra.agile.cardio.app.App;
import com.sopra.agile.cardio.app.cucumber.page.HomePage;
import com.sopra.agile.cardio.app.cucumber.page.SprintPage;
import com.sopra.agile.cardio.app.cucumber.page.SprintsPage;
import com.sopra.agile.cardio.app.cucumber.utils.CucumberScreenshot;
import com.sopra.agile.cardio.app.cucumber.utils.RestClient;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import spark.Spark;

@FluentConfiguration(driverLifecycle = ConfigurationProperties.DriverLifecycle.JVM)
public class Steps extends FluentCucumberTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(Steps.class);

    @Page
    protected HomePage homePage;

    @Page
    protected SprintsPage sprintsPage;

    @Page
    protected SprintPage sprintPage;

    @Override
    public WebDriver newWebDriver() {
        System.setProperty("webdriver.chrome.driver", "/dev/appli/chromedriver/chromedriver.exe");
        return new ChromeDriver();
    }

    @Before
    public void before(Scenario scenario) {

        LOGGER.debug("Before scenario '{}'", scenario.getName());

        super.before(scenario);

        LOGGER.debug("Start server");
        List<String> params = new ArrayList<String>();
        params.add("reset");
        App.main(params.toArray(new String[params.size()]));
        while (!RestClient.isAPIStarted()) {
            try {
                LOGGER.debug("wait... ===============================================================================");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }
        LOGGER.debug("Server started");
    }

    @After
    public void after(Scenario scenario) {

        LOGGER.debug("After scenario '{}'", scenario.getName());
        if (scenario.isFailed()) {
            CucumberScreenshot.captureScreenshot(this, scenario.getName().replace(' ', '_'));
        }

        LOGGER.debug("Stop server");
        Spark.stop();
        while (RestClient.isAPIStarted()) {
            try {
                LOGGER.debug("wait... ===============================================================================");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }
        LOGGER.debug("Server stopped");

        super.after(scenario);
    }

    @Given("^I am on the (.+) page$")
    public void i_am_on_page(String pageName) throws Throwable {
        LOGGER.debug("====================================================GIVEN i_am_on_page {}", pageName);
        window().maximize();
        FluentPage page = null;
        if ("home".equals(pageName)) {
            page = homePage;
        } else if ("sprints".equals(pageName)) {
            page = sprintsPage;
        } else if ("sprint".equals(pageName)) {
            page = sprintPage;
        }
        page.go();
        await().atMost(3, TimeUnit.SECONDS).untilPage(page).isAt();
    }

    @When("^I go on sprints page$")
    public void i_go_on_sprints_page() throws Throwable {
        homePage.goToSprints();
        await().atMost(3, TimeUnit.SECONDS).untilPage(sprintsPage).isAt();
    }

    @Then("^it should have no sprint information$")
    public void it_should_have_been_a_success() throws Throwable {
        assertEquals("no pending sprint", $("#sprint-name").text());
        assertEquals("", $("#sprint-goal").text());
        assertEquals("", $("#from-day").text());
        assertEquals("", $("#from-month").text());
        assertEquals("", $("#to-day").text());
        assertEquals("", $("#to-month").text());
    }

    @Then("^there is no error$")
    public void there_is_no_error() throws Throwable {
        sprintsPage.errorTextIs("");
    }

    @Then("^it should have no sprints$")
    public void it_should_have_no_sprints() throws Throwable {
        sprintsPage.sprintListIsEmpty();
    }

}
