package com.sopra.agile.cardio.back.service;

public interface ConfigService {

    String getVersion();

    String getProperty(String key);

    int getIntProperty(String key);

    boolean getBooleanProperty(String key);

}
