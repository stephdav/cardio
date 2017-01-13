package com.sopra.agile.cardio.app;

import static spark.Spark.port;
import static spark.Spark.threadPool;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.sopra.agile.cardio.back.config.CardioBack;
import com.sopra.agile.cardio.back.controller.RestController;
import com.sopra.agile.cardio.back.rest.RestConfig;
import com.sopra.agile.cardio.back.service.ConfigService;
import com.sopra.agile.cardio.back.utils.DatabaseSetup;
import com.sopra.agile.cardio.ui.UIConfig;

public class App {

    public static void main(String[] args) {

        databaseSetup(args);

        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(CardioBack.class);

        initWebServer(ctx);

        // Setup UI
        new UIConfig();
        // Setup REST
        new RestConfig(ctx.getBean(RestController.class));

        // Enable Route overview for debugging purpose
        // RouteOverview.enableRouteOverview("/debug/routeoverview");

        ctx.registerShutdownHook();
    }

    private static void databaseSetup(String[] args) {
        if (args != null) {
            for (String profile : args) {
                DatabaseSetup.addScript(profile);
            }
        }
    }

    private static void initWebServer(AnnotationConfigApplicationContext ctx) {

        ConfigService config = ctx.getBean(ConfigService.class);

        int port = config.getIntProperty("server.port");
        if (port != 0) {
            port(port);
        }

        int maxThreads = config.getIntProperty("server.maxThreads");
        if (maxThreads != 0) {
            threadPool(maxThreads);
        }

    }

}