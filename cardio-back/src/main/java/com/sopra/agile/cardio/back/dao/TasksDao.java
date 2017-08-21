package com.sopra.agile.cardio.back.dao;

import java.util.List;

import com.sopra.agile.cardio.common.exception.CardioTechnicalException;
import com.sopra.agile.cardio.common.model.Tasks;

public interface TasksDao {

    static final String DATABASE_FAILURE = "Failure when accessing database";

    List<Tasks> all() throws CardioTechnicalException;

    Tasks add(Tasks entity) throws CardioTechnicalException;

    List<Tasks> findByStory(long id) throws CardioTechnicalException;

    void removeByStory(long id) throws CardioTechnicalException;

    List<Tasks> findBetween(String start, String end) throws CardioTechnicalException;

    Tasks insertOrUpdate(Tasks entity) throws CardioTechnicalException;
}
