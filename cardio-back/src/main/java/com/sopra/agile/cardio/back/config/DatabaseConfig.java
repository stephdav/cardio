package com.sopra.agile.cardio.back.config;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import com.sopra.agile.cardio.back.utils.DatabaseSetup;

@Configuration
@PropertySource("classpath:cardio.properties")
public class DatabaseConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseConfig.class);

    private static final String PROPERTY_NAME_DATABASE_DRIVER = "db.driver";
    private static final String PROPERTY_NAME_DATABASE_PASSWORD = "db.password";
    private static final String PROPERTY_NAME_DATABASE_URL = "db.url";
    private static final String PROPERTY_NAME_DATABASE_USERNAME = "db.username";

    @Value("classpath:db/sql/create-db.sql")
    private Resource schemaScript;

    @Value("classpath:db/sql/data-clear.sql")
    private Resource dataCleanScript;

    @Value("classpath:db/sql/data-populate.sql")
    private Resource dataPopulateScript;

    @Autowired
    private Environment env;

    @Bean
    public DataSource dataSource() {
        DataSource ds = null;
        try {
            // Check params
            getEnv(PROPERTY_NAME_DATABASE_DRIVER);
            String url = getEnv(PROPERTY_NAME_DATABASE_URL);
            getEnv(PROPERTY_NAME_DATABASE_USERNAME);
            getEnv(PROPERTY_NAME_DATABASE_PASSWORD);
            LOGGER.info("Start using database URL {}", url);
            ds = realDataSource();
        } catch (IllegalStateException ex) {
            // Missing params
            LOGGER.warn("Missing database properties");
            LOGGER.warn("Start using inMemory database");
            ds = embeddedDataSource();
        }
        return ds;
    }

    @Bean
    public DataSourceInitializer dataSourceInitializer(final DataSource dataSource) {
        final DataSourceInitializer initializer = new DataSourceInitializer();
        initializer.setDataSource(dataSource);
        initializer.setDatabasePopulator(databasePopulator());
        return initializer;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(final DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    private DataSource realDataSource() {
        DriverManagerDataSource dmds = new DriverManagerDataSource();
        dmds.setDriverClassName(getEnv(PROPERTY_NAME_DATABASE_DRIVER));
        dmds.setUrl(getEnv(PROPERTY_NAME_DATABASE_URL));
        dmds.setUsername(getEnv(PROPERTY_NAME_DATABASE_USERNAME));
        dmds.setPassword(getEnv(PROPERTY_NAME_DATABASE_PASSWORD));
        return dmds;
    }

    private DataSource embeddedDataSource() {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        EmbeddedDatabase db = builder.setType(EmbeddedDatabaseType.H2).build();
        return db;
    }

    private DatabasePopulator databasePopulator() {
        final ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(schemaScript);

        if (DatabaseSetup.getScripts().contains("reset")) {
            populator.addScript(dataCleanScript);
        }
        for (String s : DatabaseSetup.getScripts()) {
            if (!"reset".equals(s)) {
                populator.addScript(getResource(s));
            }
        }
        return populator;
    }

    private Resource getResource(String script) {
        return new ClassPathResource("db/sql/" + script);
    }

    private String getEnv(String param) {
        return env.getRequiredProperty(param);
    }

}
