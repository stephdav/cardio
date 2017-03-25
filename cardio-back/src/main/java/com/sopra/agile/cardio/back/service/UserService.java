package com.sopra.agile.cardio.back.service;

import com.sopra.agile.cardio.common.exception.CardioTechnicalException;
import com.sopra.agile.cardio.common.model.Parameter;
import com.sopra.agile.cardio.common.model.User;

public interface UserService extends BaseService<User> {

    Parameter count() throws CardioTechnicalException;

    User findByLogin(String name);

    String remove(String userId);

    String export() throws CardioTechnicalException;

}
