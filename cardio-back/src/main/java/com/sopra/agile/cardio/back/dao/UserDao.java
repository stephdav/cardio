package com.sopra.agile.cardio.back.dao;

import com.sopra.agile.cardio.common.exception.CardioTechnicalException;
import com.sopra.agile.cardio.common.model.User;

public interface UserDao extends BaseLongDao<User> {

    User findByLogin(String login) throws CardioTechnicalException;

}
