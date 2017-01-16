package com.sopra.agile.cardio.app;

import org.fluentlenium.adapter.junit.FluentTest;
import org.fluentlenium.core.annotation.Page;
import org.fluentlenium.core.hook.wait.Wait;
import org.junit.Rule;

import com.sopra.agile.cardio.app.page.HomePage;
import com.sopra.agile.cardio.app.page.SprintPage;
import com.sopra.agile.cardio.app.page.SprintsPage;
import com.sopra.agile.cardio.app.utils.ScreenshotTestRule;

@Wait
public class AbstractBaseTest extends FluentTest {

    @Override
    public String getWebDriver() {
        return "chrome";
    }

    @Rule
    public ScreenshotTestRule screenshot = new ScreenshotTestRule(this);

    @Page
    protected HomePage homePage;

    @Page
    protected SprintsPage sprintsPage;

    @Page
    protected SprintPage sprintPage;

}