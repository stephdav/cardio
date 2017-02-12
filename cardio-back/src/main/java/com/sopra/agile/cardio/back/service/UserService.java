package com.sopra.agile.cardio.back.service;

import com.sopra.agile.cardio.common.model.User;

public interface UserService extends BaseService<User> {

    User findByLogin(String name);

    String remove(String userId);

}
