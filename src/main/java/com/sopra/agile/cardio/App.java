package com.sopra.agile.cardio;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.sopra.agile.cardio.config.AppConfig;
import com.sopra.agile.cardio.config.RestConfig;
import com.sopra.agile.cardio.controller.RestController;

public class App {

    public static void main(String[] args) {

        @SuppressWarnings("resource")
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);

        // Setup REST
        new RestConfig(ctx.getBean(RestController.class));

        // Enable Route overview for debugging purpose
        // RouteOverview.enableRouteOverview("/debug/routeoverview");

        ctx.registerShutdownHook();

    }

}