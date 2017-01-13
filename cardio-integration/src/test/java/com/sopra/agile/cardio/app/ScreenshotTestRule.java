package com.sopra.agile.cardio.app;

import org.fluentlenium.adapter.junit.FluentTest;
import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

public class ScreenshotTestRule implements MethodRule {

    private FluentTest testClass;

    public ScreenshotTestRule(FluentTest test) {
        this.testClass = test;
    }

    @Override
    public Statement apply(Statement statement, FrameworkMethod frameworkMethod, Object o) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                try {
                    statement.evaluate();
                } catch (Throwable t) {
                    captureScreenshot(frameworkMethod.getName());
                    throw t;
                }
            }

            public void captureScreenshot(String filename) {
                testClass.takeScreenShot(filename + ".png");
            }

        };
    }

}
