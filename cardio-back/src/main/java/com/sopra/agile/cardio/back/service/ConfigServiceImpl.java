package com.sopra.agile.cardio.back.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
@PropertySource("classpath:cardio.properties")
public class ConfigServiceImpl implements ConfigService {

    private static final String DEFAULT_VALUE = "";

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigServiceImpl.class);

    @Autowired
    private Environment env;

    @Override
    public String getVersion() {
        return "VERSION";
    }

    @Override
    public String getProperty(String key) {
        String value = DEFAULT_VALUE;
        try {
            value = env.getRequiredProperty(key);
        } catch (IllegalStateException ex) {
            LOGGER.warn("Unknown parameter '{}'", key);
        }
        return value;
    }

    @Override
    public int getIntProperty(String key) {
        String value = getProperty(key);
        int val = 0;
        if (!value.isEmpty()) {
            try {
                val = Integer.parseInt(value);
            } catch (NumberFormatException ex) {
                LOGGER.warn("Bad integer '{}'", value);
            }
        }
        return val;
    }

    @Override
    public boolean getBooleanProperty(String key) {
        return Boolean.parseBoolean(getProperty(key));
    }
}
