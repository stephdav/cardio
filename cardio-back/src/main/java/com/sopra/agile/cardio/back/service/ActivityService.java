package com.sopra.agile.cardio.back.service;

import com.sopra.agile.cardio.common.exception.CardioFunctionalException;
import com.sopra.agile.cardio.common.exception.CardioTechnicalException;
import com.sopra.agile.cardio.common.model.Activity;

public interface ActivityService extends BaseService<Activity> {

    Activity findByName(String name);

    Activity update(Activity activity) throws CardioTechnicalException, CardioFunctionalException;

    Activity patch(Activity activity) throws CardioTechnicalException, CardioFunctionalException;
}
