package com.sopra.agile.cardio.back.utils;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Service;

@Service
public class DatabaseScripts {

    @Autowired
    private DataSource dataSource;

    public DatabaseScripts() {
        // static class
    }

    public void applyScript(String script) {
        final ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(getResource(script));
        populator.execute(dataSource);
    }

    private Resource getResource(String script) {
        return new ClassPathResource("db/sql/" + script);
    }
}
