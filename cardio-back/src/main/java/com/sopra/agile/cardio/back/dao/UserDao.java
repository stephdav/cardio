package com.sopra.agile.cardio.back.dao;

import java.util.List;

import com.sopra.agile.cardio.common.exception.CardioTechnicalException;
import com.sopra.agile.cardio.common.model.User;

public interface UserDao {

    static final String DATABASE_FAILURE = "Failure when accessing database";

    List<User> all() throws CardioTechnicalException;

    User find(long id) throws CardioTechnicalException;

    User add(User entity) throws CardioTechnicalException;

    void remove(long id) throws CardioTechnicalException;

    User findByLogin(String login) throws CardioTechnicalException;

}
