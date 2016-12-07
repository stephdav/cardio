package com.sopra.agile.cardio.controller;

import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.sopra.agile.cardio.model.ObjectMapper;
import com.sopra.agile.cardio.model.User;
import com.sopra.agile.cardio.service.UserService;
import com.sopra.agile.cardio.service.UserServiceImpl;
import com.sopra.agile.cardio.utils.Paginate;

import spark.Request;
import spark.Response;

@Controller
public class RestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserService svcUser;

    // === USERS =============================================================

    public List<User> getAllUsers(Request req, Response res) {

        res.type("application/json");
        List<User> response = svcUser.all();

        // sort results
        if (req.queryParams("sort") != null) {
            String key = req.queryParams("sort");
            LOGGER.debug("sort=" + key);
            if ("login".equals(key)) {
                response.sort(Comparator.comparing(User::getLogin));
            } else if ("firstname".equals(key)) {
                response.sort(Comparator.comparing(User::getFirstname));
            } else if ("lastname".equals(key)) {
                response.sort(Comparator.comparing(User::getLastname));
            } else {
                LOGGER.debug("unknown sorting key {}", key);
                response.sort(Comparator.comparing(User::getId));
            }
        }

        // paginate results
        List<User> response2 = new Paginate<User>(User.class).paginate(req, res, response);

        return response2;
    }

    public User getUser(Request req, Response res, String id) {
        res.type("application/json");
        User user = svcUser.find(id);
        if (user != null) {
            res.status(200);
        } else {
            res.status(204);
        }
        return user;
    }

    public String createUser(Request req, Response res) {
        User usr = svcUser.add(new ObjectMapper<User>(User.class).parse(req.body()));
        res.status(201);
        res.header("Location", "/api/users/" + usr.getId());
        return "";
    }

    public String deleteUser(Request req, Response res, String id) {
        svcUser.remove(id);
        res.status(200);
        return "OK";
    }

}
