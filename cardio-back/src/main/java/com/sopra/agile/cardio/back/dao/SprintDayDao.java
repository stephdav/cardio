package com.sopra.agile.cardio.back.dao;

import java.util.List;

import com.sopra.agile.cardio.common.exception.CardioTechnicalException;
import com.sopra.agile.cardio.common.model.SprintDay;

public interface SprintDayDao extends BaseDao<SprintDay> {

    List<SprintDay> findBetween(String start, String end) throws CardioTechnicalException;

    SprintDay findLastBetween(String start, String end) throws CardioTechnicalException;

    SprintDay insertOrUpdate(SprintDay entity) throws CardioTechnicalException;

}
