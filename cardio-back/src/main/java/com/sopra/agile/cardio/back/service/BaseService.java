package com.sopra.agile.cardio.back.service;

import java.util.List;

import com.sopra.agile.cardio.common.exception.CardioFunctionalException;
import com.sopra.agile.cardio.common.exception.CardioTechnicalException;

public interface BaseService<T> {

    List<T> all();

    T find(String id);

    T add(T task) throws CardioTechnicalException, CardioFunctionalException;

}
