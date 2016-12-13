package com.sopra.agile.cardio.back.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.sopra.agile.cardio.back.dao.utils.UIDGenerator;
import com.sopra.agile.cardio.back.dao.utils.UserMapper;
import com.sopra.agile.cardio.common.model.User;

@Service
public class UserDaoImpl implements UserDao {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserDaoImpl.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public UserDaoImpl() {
		// Empty constructor
	}

	public UserDaoImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<User> all() {
		LOGGER.info("all ...");
		String sql = "select * from USERS";
		List<User> users = jdbcTemplate.query(sql, new UserMapper());
		return users;
	}

	@Override
	public User find(String id) {
		LOGGER.info("find '{}' ...", id);
		String sql = "select * from USERS where id = ?";
		User usr = null;
		try {
			usr = jdbcTemplate.queryForObject(sql, new Object[] { id }, new UserMapper());
		} catch (EmptyResultDataAccessException notFound) {
			LOGGER.info("No result found with id '{}'", id);
		}
		return usr;
	}

	@Override
	public User add(User user) {
		LOGGER.info("add ...");
		String sql = "insert into USERS(ID, LOGIN, FIRSTNAME, LASTNAME) values (?, ?, ?, ?)";
		user.setId(UIDGenerator.getUniqueId("USR"));
		jdbcTemplate.update(sql, user.getId(), user.getLogin(), user.getFirstname(), user.getLastname());
		return user;
	}

	@Override
	public void remove(String id) {
		LOGGER.info("remove '{}' ...", id);
		String SQL = "delete from USERS where id = ?";
		jdbcTemplate.update(SQL, id);
	}

}
