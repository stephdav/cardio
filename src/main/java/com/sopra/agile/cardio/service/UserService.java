package com.sopra.agile.cardio.service;

import com.sopra.agile.cardio.model.User;

public interface UserService extends BaseService<User> {

	String remove(String userId);

}
