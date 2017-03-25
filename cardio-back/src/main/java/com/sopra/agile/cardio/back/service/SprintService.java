package com.sopra.agile.cardio.back.service;

import java.util.List;

import com.sopra.agile.cardio.common.exception.CardioFunctionalException;
import com.sopra.agile.cardio.common.exception.CardioTechnicalException;
import com.sopra.agile.cardio.common.model.Parameter;
import com.sopra.agile.cardio.common.model.Sprint;
import com.sopra.agile.cardio.common.model.SprintData;

public interface SprintService extends BaseService<Sprint> {

    List<Parameter> count() throws CardioTechnicalException;

    Sprint findByName(String name) throws CardioTechnicalException;

    List<Sprint> findByDay(String day) throws CardioTechnicalException;

    Sprint update(Sprint sprint) throws CardioTechnicalException, CardioFunctionalException;

    Sprint patch(Sprint sprint) throws CardioTechnicalException, CardioFunctionalException;

    SprintData findData(String id) throws CardioTechnicalException, CardioFunctionalException;

    void updateData(String id, SprintData data) throws CardioTechnicalException, CardioFunctionalException;

    String export() throws CardioTechnicalException;
}
