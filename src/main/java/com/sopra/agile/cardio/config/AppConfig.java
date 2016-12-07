package com.sopra.agile.cardio.config;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.sopra.agile.cardio.controller.ControllerConfig;
import com.sopra.agile.cardio.dao.DaoConfig;
import com.sopra.agile.cardio.service.ServiceConfig;

@Configuration
@Import(value = { DatabaseConfig.class, ControllerConfig.class, ServiceConfig.class, DaoConfig.class })
public class AppConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppConfig.class);

    @PostConstruct
    public void initApp() {
        LOGGER.info("Application initialized");
    }
}
