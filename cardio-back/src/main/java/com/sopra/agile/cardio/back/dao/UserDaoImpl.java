package com.sopra.agile.cardio.back.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.sopra.agile.cardio.common.exception.CardioTechnicalException;
import com.sopra.agile.cardio.common.model.User;

@Service
public class UserDaoImpl implements UserDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDaoImpl.class);

    private static final String SQL_ALL = "select * from USERS";
    private static final String SQL_FIND_BY_ID = "select * from USERS where id=?";
    private static final String SQL_FIND_BY_LOGIN = "select * from USERS where login=?";
    private static final String SQL_INSERT = "insert into USERS(LOGIN, FIRSTNAME, LASTNAME) values (?, ?, ?)";
    private static final String SQL_DELETE = "delete from USERS where id=?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public UserDaoImpl() {
        // Empty constructor
    }

    public UserDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<User> all() throws CardioTechnicalException {
        LOGGER.debug("[DAO] all users ...");
        List<User> users = null;
        try {
            users = jdbcTemplate.query(SQL_ALL, new UserMapper());
        } catch (Exception ex) {
            throw new CardioTechnicalException(DATABASE_FAILURE, ex);
        }
        return users;
    }

    @Override
    public User find(long id) throws CardioTechnicalException {
        LOGGER.debug("[DAO] find user '{}' ...", id);
        User user = null;
        try {
            user = jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new Object[] { id }, new UserMapper());
        } catch (EmptyResultDataAccessException notFound) {
            LOGGER.info("No result found with id '{}'", id);
        } catch (Exception ex) {
            throw new CardioTechnicalException(DATABASE_FAILURE, ex);
        }
        return user;
    }

    @Override
    public User findByLogin(String login) throws CardioTechnicalException {
        LOGGER.debug("[DAO] find user with login '{}' ...", login);
        User user = null;
        try {
            user = jdbcTemplate.queryForObject(SQL_FIND_BY_LOGIN, new Object[] { login }, new UserMapper());
        } catch (EmptyResultDataAccessException notFound) {
            LOGGER.info("No result found with name '{}'", login);
        } catch (Exception ex) {
            throw new CardioTechnicalException(DATABASE_FAILURE, ex);
        }
        return user;
    }

    @Override
    public User add(User user) throws CardioTechnicalException {
        LOGGER.debug("[DAO] add new user ...");
        try {
            jdbcTemplate.update(SQL_INSERT, user.getLogin(), user.getFirstname(), user.getLastname());
        } catch (Exception ex) {
            throw new CardioTechnicalException(DATABASE_FAILURE, ex);
        }
        return user;
    }

    @Override
    public void remove(long id) throws CardioTechnicalException {
        LOGGER.info("[DAO] remove '{}' ...", id);
        try {
            jdbcTemplate.update(SQL_DELETE, id);
        } catch (Exception ex) {
            throw new CardioTechnicalException(DATABASE_FAILURE, ex);
        }
    }

}
