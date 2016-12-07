package com.sopra.agile.cardio.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

@Configuration
public class DatabaseConfig {

	@Value("classpath:db/sql/create-db.sql")
	private Resource schemaScript;

	@Value("classpath:db/sql/insert-data.sql")
	private Resource dataScript;

	// @Bean
	// public DataSource dataSource() {
	// // no need shutdown, EmbeddedDatabaseFactoryBean will take care of this
	// EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
	// EmbeddedDatabase db =
	// builder.setType(EmbeddedDatabaseType.H2).addScript("db/sql/create-db.sql")
	// .addScript("db/sql/insert-data.sql").build();
	// return db;
	// }

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dmds = new DriverManagerDataSource();
		dmds.setDriverClassName("org.h2.Driver");
		dmds.setUrl("jdbc:h2:./testDb");
		dmds.setUsername("sa");
		dmds.setPassword("sa");
		return dmds;
	}

	@Bean
	public DataSourceInitializer dataSourceInitializer(final DataSource dataSource) {

		final DataSourceInitializer initializer = new DataSourceInitializer();
		initializer.setDataSource(dataSource);
		initializer.setDatabasePopulator(databasePopulator());
		// initializer.setDatabaseCleaner(databaseCleaner());
		return initializer;
	}

	private DatabasePopulator databasePopulator() {
		final ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
		populator.addScript(schemaScript);
		// populator.addScript(dataScript);
		return populator;
	}

	@Bean
	public JdbcTemplate jdbcTemplate(final DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}

}
