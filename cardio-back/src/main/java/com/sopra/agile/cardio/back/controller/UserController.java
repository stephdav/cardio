package com.sopra.agile.cardio.back.controller;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.sopra.agile.cardio.back.model.ObjectMapper;
import com.sopra.agile.cardio.back.service.UserService;
import com.sopra.agile.cardio.back.utils.Paginate;
import com.sopra.agile.cardio.common.exception.CardioFunctionalException;
import com.sopra.agile.cardio.common.exception.CardioTechnicalException;
import com.sopra.agile.cardio.common.model.Parameter;
import com.sopra.agile.cardio.common.model.User;

import spark.Request;
import spark.Response;

@Controller
public class UserController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService svcUser;

    public UserController() {
        // Empty constructor
    }

    // === USERS =============================================================

    public Parameter getCount(Request req, Response res) {
        res.type("application/json");
        Parameter response = null;

        try {
            response = svcUser.count();
            res.status(200);
        } catch (CardioTechnicalException e) {
            res.status(500);
        }

        return response;
    }

    public List<User> getAllUsers(Request req, Response res) {
        res.type("application/json");
        List<User> response = null;

        try {
            List<User> list = svcUser.all();
            if (list != null) {
                sortUsers(list, req.queryParams("sortName"), req.queryParams("sortOrder"));
                response = new Paginate<User>(User.class).paginate(req, res, list);
            }
        } catch (CardioFunctionalException e) {
            res.status(400);
        } catch (CardioTechnicalException e) {
            res.status(500);
        }

        return response;
    }

    public User getUser(Request req, Response res, String id) {
        res.type("application/json");
        User user = null;

        try {
            user = svcUser.find(id);
            if (user != null) {
                res.status(200);
            } else {
                res.status(204);
            }
        } catch (CardioFunctionalException e) {
            res.status(400);
        } catch (CardioTechnicalException e) {
            res.status(500);
        }

        return user;
    }

    public String createUser(Request req, Response res) {
        String response = "OK";

        try {
            User usr = svcUser.add(new ObjectMapper<User>(User.class).parse(req.body()));
            res.status(201);
            res.header(LOCATION, "/api/activities/" + usr.getId());
        } catch (CardioFunctionalException e) {
            res.status(400);
            response = e.getMessage();
        } catch (CardioTechnicalException e) {
            res.status(500);
            response = e.getMessage();
        }

        return response;
    }

    public String deleteUser(Request req, Response res, String id) {
        svcUser.remove(id);
        res.status(200);
        return "OK";
    }

    private void sortUsers(List<User> response, String key, String sortOrder) {
        // sort results
        if (key != null) {
            LOGGER.debug("sort=" + key);
            if ("login".equals(key)) {
                response.sort(Comparator.comparing(User::getLogin));
            } else if ("firstname".equals(key)) {
                response.sort(Comparator.comparing(User::getFirstname));
            } else if ("lastname".equals(key)) {
                response.sort(Comparator.comparing(User::getLastname));
            } else {
                LOGGER.debug("unknown sorting key {}", key);
                response.sort(Comparator.comparing(User::getLogin));
            }
        } else {
            // default sorting
            response.sort(Comparator.comparing(User::getLogin));
        }
        if ("desc".equals(sortOrder)) {
            LOGGER.debug("reverse order");
            Collections.reverse(response);
        }
    }

    public HttpServletResponse exportUsers(Request req, Response res) {
        String data = null;
        try {
            data = svcUser.export();
        } catch (CardioTechnicalException e) {
            data = e.getMessage();
        }
        return export(res, "users.sql", data);
    }
}
