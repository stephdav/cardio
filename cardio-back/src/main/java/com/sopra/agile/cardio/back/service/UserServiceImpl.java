package com.sopra.agile.cardio.back.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sopra.agile.cardio.back.dao.UserDao;
import com.sopra.agile.cardio.back.utils.Fields;
import com.sopra.agile.cardio.common.exception.CardioFunctionalException;
import com.sopra.agile.cardio.common.exception.CardioTechnicalException;
import com.sopra.agile.cardio.common.model.User;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    public UserServiceImpl() {
        // Empty constructor
    }

    @Override
    public List<User> all() {
        LOGGER.info("all ...");

        List<User> response = null;
        try {
            response = userDao.all();
        } catch (CardioTechnicalException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return response;
    }

    @Override
    public User find(String id) {
        LOGGER.info("find '{}' ...", id);
        User response = null;
        try {
            response = userDao.find(id);
        } catch (CardioTechnicalException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return response;
    }

    @Override
    public User findByLogin(String name) {
        LOGGER.info("findByName '{}' ...", name);

        User response = null;
        try {
            response = userDao.findByLogin(name);
        } catch (CardioTechnicalException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return response;
    }

    @Override
    public User add(User user) throws CardioTechnicalException, CardioFunctionalException {
        LOGGER.info("add ...");
        checkUserProperties(user);
        checkUserDuplicate(user);
        return userDao.add(user);
    }

    @Override
    public String remove(String id) {
        LOGGER.info("remove '{}' ...", id);
        User usr = find(id);
        if (usr != null) {
            try {
                userDao.remove(id);
            } catch (CardioTechnicalException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return id;
    }

    private void checkUserProperties(User user) throws CardioFunctionalException {
        if (user == null) {
            LOGGER.error("activity can't be null");
            throw new CardioFunctionalException("activity can't be null");
        }
        Fields.checkField("login", user.getLogin(), 2, 8);
        Fields.checkField("firstname", user.getFirstname(), 1, 64);
        Fields.checkField("lastname", user.getLastname(), 1, 64);
    }

    private void checkUserDuplicate(User user) throws CardioFunctionalException, CardioTechnicalException {
        // Looking for an activity with same name
        User found = findByLogin(user.getLogin());
        if (found != null && (user.getId() == null || !user.getId().equals(found.getId()))) {
            LOGGER.error("A user '{}' already exists", user.getLogin());
            throw new CardioFunctionalException("user with same login already exists");
        }
    }

}
