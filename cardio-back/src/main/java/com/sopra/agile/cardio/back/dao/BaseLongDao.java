package com.sopra.agile.cardio.back.dao;

import java.util.List;

import com.sopra.agile.cardio.common.exception.CardioTechnicalException;
import com.sopra.agile.cardio.common.model.Parameter;

public interface BaseLongDao<T> {

    static final String DATABASE_FAILURE = "Failure when accessing database";

    Parameter count() throws CardioTechnicalException;

    List<T> all() throws CardioTechnicalException;

    T find(long id) throws CardioTechnicalException;

    T add(T entity) throws CardioTechnicalException;

    void remove(long id) throws CardioTechnicalException;

}
