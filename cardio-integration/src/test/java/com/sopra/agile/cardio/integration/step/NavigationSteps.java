package com.sopra.agile.cardio.integration.step;

import java.util.concurrent.TimeUnit;

import org.fluentlenium.adapter.cucumber.FluentCucumberTest;
import org.fluentlenium.configuration.ConfigurationProperties;
import org.fluentlenium.configuration.ConfigurationProperties.TriggerMode;
import org.fluentlenium.configuration.FluentConfiguration;
import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.annotation.Page;
import org.openqa.selenium.WebDriver;

import com.sopra.agile.cardio.integration.page.HomePage;
import com.sopra.agile.cardio.integration.page.SprintPage;
import com.sopra.agile.cardio.integration.page.SprintPlanningPage;
import com.sopra.agile.cardio.integration.page.SprintsPage;
import com.sopra.agile.cardio.integration.utils.CustomWebDriver;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;

@FluentConfiguration(driverLifecycle = ConfigurationProperties.DriverLifecycle.JVM, screenshotMode = TriggerMode.AUTOMATIC_ON_FAIL, screenshotPath = "target/cucumber")
public class NavigationSteps extends FluentCucumberTest {

	@Page
	private HomePage homePage;

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

	@Given("^I am on the (.+) page$")
	public void i_am_on_page(String pageName) throws Throwable {
		// window().maximize();
		FluentPage page = null;
		if ("home".equals(pageName)) {
			page = homePage;
		} else if ("sprints".equals(pageName)) {
			page = sprintsPage;
		} else if ("sprint".equals(pageName)) {
			page = sprintPage;
		} else {
			page = homePage;
		}
		page.go();
		await().atMost(3, TimeUnit.SECONDS).untilPage(page).isAt();
	}

	@When("^I go on sprints page$")
	public void i_go_on_sprints_page() throws Throwable {
		homePage.goToSprints();
		await().atMost(3, TimeUnit.SECONDS).untilPage(sprintsPage).isAt();
	}

	@When("^I go on sprint planning page$")
	public void i_go_on_sprint_planning_page() throws Throwable {
		homePage.goToSprintPlanning();
		await().atMost(3, TimeUnit.SECONDS).untilPage(sprintPlanningPage).isAt();
	}

}
