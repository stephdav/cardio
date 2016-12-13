package com.sopra.agile.cardio.back.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sopra.agile.cardio.back.dao.UserDao;
import com.sopra.agile.cardio.common.model.User;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    public UserServiceImpl() {
        // Empty constructor
    }

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public List<User> all() {
        LOGGER.info("all ...");
        return userDao.all();
    }

    @Override
    public User find(String id) {
        LOGGER.info("find '{}' ...", id);
        return userDao.find(id);
    }

    @Override
    public User add(User user) {
        LOGGER.info("add ...");
        return userDao.add(user);
    }

    @Override
    public String remove(String id) {
        LOGGER.info("remove '{}' ...", id);
        User usr = find(id);
        if (usr != null) {
            userDao.remove(id);
        }
        return id;
    }

}
