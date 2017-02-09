package com.sopra.agile.cardio.back.dao;

import java.util.List;

import com.sopra.agile.cardio.common.exception.CardioTechnicalException;

public interface BaseDao<T> {

    static final String DATABASE_FAILURE = "Failure when accessing database";

    List<T> all() throws CardioTechnicalException;

    T find(String id) throws CardioTechnicalException;

    T add(T entity) throws CardioTechnicalException;

    void remove(String id) throws CardioTechnicalException;

}
