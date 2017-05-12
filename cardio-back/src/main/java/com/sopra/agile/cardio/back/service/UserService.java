package com.sopra.agile.cardio.back.service;

import com.sopra.agile.cardio.common.exception.CardioFunctionalException;
import com.sopra.agile.cardio.common.exception.CardioTechnicalException;
import com.sopra.agile.cardio.common.model.Parameter;
import com.sopra.agile.cardio.common.model.User;

public interface UserService extends BaseService<User> {

    Parameter count() throws CardioTechnicalException;

    User findByLogin(String name);

    User update(User usr) throws CardioTechnicalException, CardioFunctionalException;

    User patch(User usr) throws CardioTechnicalException, CardioFunctionalException;

    String remove(String userId) throws CardioTechnicalException;

    String export() throws CardioTechnicalException;

}
