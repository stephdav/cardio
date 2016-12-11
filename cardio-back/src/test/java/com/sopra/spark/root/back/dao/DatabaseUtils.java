package com.sopra.spark.root.back.dao;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

public class DatabaseUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseUtils.class);

    public static JdbcTemplate initDbConnexion() {
        return initDbConnexion(null);
    }

    public static JdbcTemplate initDbConnexion(String data) {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2);
        // Create tables
        builder.addScript("db/sql/create-db.sql");
        if (data != null) {
            // Insert data
            builder.addScript("db/sql/" + data);
        }
        DataSource dataSource = builder.build();
        return new JdbcTemplate(dataSource);
    }

    public static int count(JdbcTemplate jdbcTemplate, String table) {
        String sql = String.format("select count(*) from %s", table);
        int result = jdbcTemplate.queryForObject(sql, Integer.class);
        LOGGER.info("select count(*) from {} : {}", table, result);
        return result;
    }
}
