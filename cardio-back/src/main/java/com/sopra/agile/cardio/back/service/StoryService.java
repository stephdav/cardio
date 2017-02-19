package com.sopra.agile.cardio.back.service;

import com.sopra.agile.cardio.common.exception.CardioFunctionalException;
import com.sopra.agile.cardio.common.exception.CardioTechnicalException;
import com.sopra.agile.cardio.common.model.Story;

public interface StoryService extends BaseService<Story> {

    Story update(Story story) throws CardioTechnicalException, CardioFunctionalException;

    Story patch(Story story) throws CardioTechnicalException, CardioFunctionalException;
}
