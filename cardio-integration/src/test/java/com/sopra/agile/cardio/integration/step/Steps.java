package com.sopra.agile.cardio.integration.step;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.fluentlenium.adapter.cucumber.FluentCucumberTest;
import org.fluentlenium.configuration.ConfigurationProperties;
import org.fluentlenium.configuration.ConfigurationProperties.TriggerMode;
import org.fluentlenium.configuration.FluentConfiguration;
import org.fluentlenium.core.annotation.Page;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.sopra.agile.cardio.integration.page.SprintPage;
import com.sopra.agile.cardio.integration.page.SprintPlanningPage;
import com.sopra.agile.cardio.integration.page.SprintsPage;
import com.sopra.agile.cardio.integration.utils.CustomWebDriver;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

@FluentConfiguration(driverLifecycle = ConfigurationProperties.DriverLifecycle.JVM, screenshotMode = TriggerMode.AUTOMATIC_ON_FAIL, screenshotPath = "target/cucumber")
public class Steps extends FluentCucumberTest {

    @Page
    private SprintsPage sprintsPage;

    @Page
    private SprintPage sprintPage;

    @Page
    private SprintPlanningPage sprintPlanningPage;

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

    @When("^I select sprint '(.*)'$")
    public void i_select_sprint(String name) throws Throwable {
        sprintsPage.clickOnSprint(name);
        sprintPage.isAt();
    }

    @When("^I create a sprint with name '(.*)', startdate '(.*)' and enddate '(.*)'$")
    public void i_create_a_sprint_with_name_startdate_and_enddate(String name, String startDate, String endDate)
            throws Throwable {

        if (name != null) {
            $("#sprintName").fill().with(name);
        }
        if (startDate != null) {
            getDriver().findElement(By.id("sprintStartDate")).sendKeys(startDate);
        }
        if (endDate != null) {
            getDriver().findElement(By.id("sprintEndDate")).sendKeys(endDate);
        }
        $("#addSprint").click();
    }

    @When("^I change commitment to '(.*)'$")
    public void i_change_commitment_to(String commitment) throws Throwable {
        sprintPage.updateCommitment(commitment);
    }

    @When("^I change endDate to '(.*)'$")
    public void i_change_enddate_to(String date) throws Throwable {
        getDriver().findElement(By.id("sprintEndDate")).sendKeys(date);
    }

    @When("^I validate properties$")
    public void i_validate_properties() throws Throwable {
        sprintPage.validatePropertiesUpdate();
    }

    @When("^I change data of day '(\\d+)' to '(.*)'$")
    public void i_change_data_of_day_to(int day, String done) throws Throwable {
        sprintPage.updateData(day, done);
    }

    @When("^I validate data$")
    public void i_validate_data() throws Throwable {
        sprintPage.validateDataUpdate();
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

    @Then("^there is an error '(.+)'$")
    public void there_is_an_error(String msg) throws Throwable {
        sprintsPage.errorTextIs(msg);
    }

    @Then("^it should have no sprints$")
    public void it_should_have_no_sprints() throws Throwable {
        sprintsPage.sprintListIsEmpty();
    }

    @Then("^there are (\\d+) sprints displayed over (\\d+)$")
    public void there_are_sprint_displayed_over(int page, int total) throws Throwable {
        sprintsPage.sprintListContains(page, total);
    }

    @Then("^there is a sprint with dates '(.*)' and '(.*)', name '(.*)', goal '(.*)', commitment '(.*)' and velocity '(.*)' in the list$")
    public void there_is_a_sprint_with_dates_and_name_goal_sprint_commitment_and_velocity_in_the_list(String from,
            String to, String name, String goal, String commitment, String velocity) throws Throwable {
        sprintsPage.sprintListContainsSprint(from, to, name, goal, commitment, velocity);
    }

    @Then("^the sprint properties are dates '(.*)' and '(.*)', name '(.*)', goal '(.*)' and commitment '(.*)'$")
    public void the_sprint_properties_are(String from, String to, String name, String goal, String commitment)
            throws Throwable {
        sprintPage.testSprintProperties(from, to, name, goal, commitment);
    }

    @Then("^the sprint data are: (.*)$")
    public void the_sprint_data_are(List<String> values) throws Throwable {
        sprintPage.testSprintData(values);
    }

    @Then("^the trend values are '(.*)', '(.*)' and '(.*)'$")
    public void the_trend_values_are_and(String worst, String average, String best) throws Throwable {
        sprintPlanningPage.testTrendValues(worst, average, best);
    }
}
