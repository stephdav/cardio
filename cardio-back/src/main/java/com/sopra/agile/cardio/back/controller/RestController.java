package com.sopra.agile.cardio.back.controller;

import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.sopra.agile.cardio.back.model.ObjectMapper;
import com.sopra.agile.cardio.back.model.Parameter;
import com.sopra.agile.cardio.back.service.ConfigService;
import com.sopra.agile.cardio.back.service.SprintService;
import com.sopra.agile.cardio.back.service.UserService;
import com.sopra.agile.cardio.back.service.UserServiceImpl;
import com.sopra.agile.cardio.back.utils.Paginate;
import com.sopra.agile.cardio.common.model.Chart;
import com.sopra.agile.cardio.common.model.Sprint;
import com.sopra.agile.cardio.common.model.SprintData;
import com.sopra.agile.cardio.common.model.User;

import spark.Request;
import spark.Response;

@Controller
public class RestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private ConfigService svcConfig;

    @Autowired
    private UserService svcUser;

    @Autowired
    private SprintService svcSprint;

    // === CONFIG ============================================================

    public Parameter getParameter(Request req, Response res, String key) {
        res.type("application/json");
        String value = svcConfig.getProperty(key);
        if (value != null) {
            res.status(200);
        } else {
            res.status(204);
        }
        return new Parameter(key, value);
    }

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

    // === SPRINTS ===========================================================

    public List<Sprint> getAllSprints(Request req, Response res) {

        res.type("application/json");
        List<Sprint> response = svcSprint.all();

        // sort results
        if (req.queryParams("sort") != null) {
            String key = req.queryParams("sort");
            LOGGER.debug("sort=" + key);
            if ("name".equals(key)) {
                response.sort(Comparator.comparing(Sprint::getName));
            } else if ("startdate".equals(key)) {
                response.sort(Comparator.comparing(Sprint::getStartDate));
            } else if ("enddate".equals(key)) {
                response.sort(Comparator.comparing(Sprint::getEndDate));
            } else {
                LOGGER.debug("unknown sorting key {}", key);
                response.sort(Comparator.comparing(Sprint::getId));
            }
        }

        // paginate results
        List<Sprint> response2 = new Paginate<Sprint>(Sprint.class).paginate(req, res, response);

        return response2;
    }

    public Sprint getSprint(Request req, Response res, String id) {
        res.type("application/json");
        Sprint sprint = svcSprint.find(id);
        if (sprint != null) {
            res.status(200);
        } else {
            res.status(204);
        }
        return sprint;
    }

    public Chart getSprintData(Request req, Response res, String id) {
        res.type("application/json");
        Chart chart = svcSprint.data(id);
        if (chart != null) {
            res.status(200);
        } else {
            res.status(204);
        }
        return chart;
    }

    public String createSprint(Request req, Response res) {
        Sprint sprint = svcSprint.add(new ObjectMapper<Sprint>(Sprint.class).parse(req.body()));
        res.status(201);
        res.header("Location", "/api/sprints/" + sprint.getId());
        return "";
    }

    public String updateSprintProperties(Request req, Response res, String id) {
        Sprint sprint = svcSprint.update(new ObjectMapper<Sprint>(Sprint.class).parse(req.body()));
        res.status(201);
        res.header("Location", "/api/sprints/" + sprint.getId());
        return "";
    }

    public String updateSprintData(Request req, Response res, String id) {
        Sprint sprint = svcSprint.find(id);
        if (sprint != null) {
            svcSprint.updateData(new ObjectMapper<SprintData>(SprintData.class).parse(req.body()));
            res.status(201);
            res.header("Location", "/api/sprints/" + sprint.getId());
        }
        return "";
    }

    public Sprint getCurrentSprint(Request req, Response res) {
        res.type("application/json");
        Sprint sprint = svcSprint.currentSprint();
        if (sprint != null) {
            res.status(200);
        } else {
            res.status(204);
        }
        return sprint;
    }

    public Parameter getCurrentLeftDays(Request req, Response res) {
        res.type("application/json");
        Parameter param = svcSprint.leftDays();
        if (param != null) {
            res.status(200);
        } else {
            res.status(204);
        }
        return param;
    }

    public Chart getCurrentBurndown(Request req, Response res) {
        res.type("application/json");
        Chart chart = svcSprint.burndown();
        if (chart != null) {
            res.status(200);
        } else {
            res.status(204);
        }
        return chart;
    }

}
