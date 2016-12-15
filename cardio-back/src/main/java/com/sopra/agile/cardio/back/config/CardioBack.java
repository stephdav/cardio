package com.sopra.agile.cardio.back.config;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.sopra.agile.cardio.back.controller.config.ControllerConfig;
import com.sopra.agile.cardio.back.dao.config.DaoConfig;
import com.sopra.agile.cardio.back.service.config.ServiceConfig;
import com.sopra.agile.cardio.back.utils.config.UtilConfig;

@Configuration
@Import(value = { DatabaseConfig.class, ControllerConfig.class, ServiceConfig.class, DaoConfig.class,
        UtilConfig.class })
public class CardioBack {

    private static final Logger LOGGER = LoggerFactory.getLogger(CardioBack.class);

    @PostConstruct
    public void initApp() {
        LOGGER.info("Application initialized");
    }
}
