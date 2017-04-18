package com.sopra.agile.cardio.back.service;

import java.util.List;

import com.sopra.agile.cardio.common.exception.CardioFunctionalException;
import com.sopra.agile.cardio.common.exception.CardioTechnicalException;
import com.sopra.agile.cardio.common.model.Parameter;
import com.sopra.agile.cardio.common.model.Story;

public interface StoryService extends BaseService<Story> {

    Parameter count() throws CardioTechnicalException;

    List<Parameter> countByStatus() throws CardioTechnicalException;

    Story update(Story story) throws CardioTechnicalException, CardioFunctionalException;

    Story patch(Story story) throws CardioTechnicalException, CardioFunctionalException;

    Story patch(String id, String status, String contribution, String estimate, String assignedUser)
            throws CardioTechnicalException, CardioFunctionalException;

    String export() throws CardioTechnicalException;
}
