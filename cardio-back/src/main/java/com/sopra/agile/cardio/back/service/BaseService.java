package com.sopra.agile.cardio.back.service;

import java.util.List;

import com.sopra.agile.cardio.common.exception.CardioFunctionalException;
import com.sopra.agile.cardio.common.exception.CardioTechnicalException;

public interface BaseService<T> {

    List<T> all() throws CardioTechnicalException, CardioFunctionalException;

    T find(String id) throws CardioTechnicalException, CardioFunctionalException;

    T add(T entity) throws CardioTechnicalException, CardioFunctionalException;

}
