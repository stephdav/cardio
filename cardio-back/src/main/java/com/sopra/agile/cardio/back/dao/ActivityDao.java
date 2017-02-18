package com.sopra.agile.cardio.back.dao;

import com.sopra.agile.cardio.common.exception.CardioTechnicalException;
import com.sopra.agile.cardio.common.model.Activity;

public interface ActivityDao extends BaseDao<Activity> {

    Activity findByName(String name) throws CardioTechnicalException;

    Activity update(Activity activity) throws CardioTechnicalException;
}
