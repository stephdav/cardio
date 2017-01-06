package com.sopra.agile.cardio.back.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sopra.agile.cardio.back.dao.UserDao;
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
    public User add(User user) {
        LOGGER.info("add ...");
        User response = null;
        try {
            response = userDao.add(user);
        } catch (CardioTechnicalException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return response;
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

}
