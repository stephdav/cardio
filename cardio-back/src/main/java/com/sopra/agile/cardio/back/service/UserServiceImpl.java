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
import com.sopra.agile.cardio.common.model.Parameter;
import com.sopra.agile.cardio.common.model.User;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    private static final String SQL_EXPORT = "INSERT INTO USERS(LOGIN,FIRSTNAME,LASTNAME) VALUES ('%s', '%s', '%s');\n";

    @Autowired
    private UserDao userDao;

    public UserServiceImpl() {
        // Empty constructor
    }

    @Override
    public Parameter count() throws CardioTechnicalException {
        LOGGER.debug("[SVC] count ...");

        Parameter param = userDao.count();
        if (param == null) {
            param = new Parameter("USERS", "0");
        }
        return param;
    }

    @Override
    public List<User> all() throws CardioTechnicalException {
        LOGGER.info("[SVC] all ...");
        return userDao.all();
    }

    @Override
    public User find(String id) throws CardioTechnicalException {
        LOGGER.debug("[SVC] find user '{}' ...", id);
        User response = null;
        try {
            response = find(Long.parseLong(id));
        } catch (NumberFormatException e) {
            throw new CardioTechnicalException("Bad sprint identifier", e);
        }
        return response;
    }

    private User find(long id) throws CardioTechnicalException {
        return userDao.find(id);
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
    public String remove(String id) throws CardioTechnicalException {
        LOGGER.info("remove '{}' ...", id);
        User usr = find(id);
        if (usr != null) {
            try {
                userDao.remove(Long.parseLong(id));
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
        if (found != null && user.getId() == found.getId()) {
            LOGGER.error("A user '{}' already exists", user.getLogin());
            throw new CardioFunctionalException("user with same login already exists");
        }
    }

    @Override
    public String export() throws CardioTechnicalException {
        List<User> users = all();
        StringBuffer sb = new StringBuffer();
        for (User usr : users) {
            sb.append(String.format(SQL_EXPORT, usr.getLogin(), usr.getFirstname(), usr.getLastname()));
        }
        return sb.toString();
    }

}
