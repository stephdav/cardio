package com.sopra.agile.cardio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.AbstractEnvironment;

import com.sopra.agile.cardio.config.AppConfig;
import com.sopra.agile.cardio.config.RestConfig;
import com.sopra.agile.cardio.controller.RestController;
import com.sopra.agile.cardio.utils.AppProfile;

public class App {

    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {

        activateProfiles(args);

        @SuppressWarnings("resource")
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);

        // Setup REST
        new RestConfig(ctx.getBean(RestController.class));

        // Enable Route overview for debugging purpose
        // RouteOverview.enableRouteOverview("/debug/routeoverview");

        ctx.registerShutdownHook();
    }

    private static void activateProfiles(String[] args) {
        if (args != null) {
            for (String profile : args) {
                if (AppProfile.isAuthorized(profile)) {
                    addProfile(profile);
                } else {
                    LOGGER.warn("Unauthorized profile '{}'", profile);
                }
            }
        }
    }

    private static void addProfile(String profile) {
        LOGGER.warn("Activate profile '{}'", profile);
        String profiles = System.getProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME, "");
        StringBuffer sb = new StringBuffer(profiles);
        if (sb.length() > 0) {
            sb.append(',');
        }
        sb.append(profile);
        System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME, sb.toString());
    }

}