package com.sopra.agile.cardio.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(value = { DatabaseConfig.class })
@ComponentScan({ "com.sopra.agile.cardio" })
public class AppConfig {

}
