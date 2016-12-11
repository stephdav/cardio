package com.sopra.agile.cardio.back.service;

import com.sopra.agile.cardio.back.model.User;

public interface UserService extends BaseService<User> {

	String remove(String userId);

}
