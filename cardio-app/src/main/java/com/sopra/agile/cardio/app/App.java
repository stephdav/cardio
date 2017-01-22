package com.sopra.agile.cardio.app;

import static spark.Spark.port;
import static spark.Spark.threadPool;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.sopra.agile.cardio.back.config.CardioBack;
import com.sopra.agile.cardio.back.controller.RestController;
import com.sopra.agile.cardio.back.rest.RestConfig;
import com.sopra.agile.cardio.back.service.ConfigService;
import com.sopra.agile.cardio.back.utils.DatabaseScripts;
import com.sopra.agile.cardio.back.utils.DatabaseSetup;
import com.sopra.agile.cardio.ui.UIConfig;

public class App {

    public static void main(String[] args) {

        if (args != null) {
            for (String arg : args) {
                DatabaseSetup.addScript(arg);
            }
        }

        App app = new App();
        app.start();
        app.registerStop();
    }

    private AnnotationConfigApplicationContext ctx;

    public App() {
        ctx = new AnnotationConfigApplicationContext(CardioBack.class);
    }

    public void start() {

        initWebServer();

        // Setup UI
        new UIConfig();

        // Setup REST
        new RestConfig(ctx.getBean(RestController.class));

    }

    public void registerStop() {
        ctx.registerShutdownHook();
    }

    private void initWebServer() {

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

    public void applyScript(String script) {
        DatabaseScripts ds = ctx.getBean(DatabaseScripts.class);
        ds.applyScript(script);
    }

}