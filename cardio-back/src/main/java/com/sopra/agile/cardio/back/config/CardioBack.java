package com.sopra.agile.cardio.back.config;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.sopra.agile.cardio.back.controller.ControllerConfig;
import com.sopra.agile.cardio.back.dao.DaoConfig;
import com.sopra.agile.cardio.back.service.ServiceConfig;

@Configuration
@Import(value = { DatabaseConfig.class, ControllerConfig.class, ServiceConfig.class, DaoConfig.class })
public class CardioBack {

    private static final Logger LOGGER = LoggerFactory.getLogger(CardioBack.class);

    @PostConstruct
    public void initApp() {
        LOGGER.info("Application initialized");
    }
}
