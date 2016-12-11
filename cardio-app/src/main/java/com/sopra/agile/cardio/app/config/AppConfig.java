package com.sopra.agile.cardio.app.config;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppConfig.class);

    @PostConstruct
    public void initApp() {
        LOGGER.info("Application initialized");
    }
}
