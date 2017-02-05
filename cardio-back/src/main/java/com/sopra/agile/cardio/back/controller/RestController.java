package com.sopra.agile.cardio.back.controller;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.sopra.agile.cardio.back.model.ObjectMapper;
import com.sopra.agile.cardio.back.model.Parameter;
import com.sopra.agile.cardio.back.service.ConfigService;
import com.sopra.agile.cardio.back.service.ProjectService;
import com.sopra.agile.cardio.back.service.SprintService;
import com.sopra.agile.cardio.back.service.UserService;
import com.sopra.agile.cardio.back.service.UserServiceImpl;
import com.sopra.agile.cardio.back.utils.Paginate;
import com.sopra.agile.cardio.common.exception.CardioFunctionalException;
import com.sopra.agile.cardio.common.exception.CardioTechnicalException;
import com.sopra.agile.cardio.common.model.ProjectDataDetails;
import com.sopra.agile.cardio.common.model.ProjectVision;
import com.sopra.agile.cardio.common.model.Sprint;
import com.sopra.agile.cardio.common.model.SprintData;
import com.sopra.agile.cardio.common.model.User;

import spark.Request;
import spark.Response;

@Controller
public class RestController {

    private static final String LOCATION = "Location";

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private ConfigService svcConfig;

    @Autowired
    private UserService svcUser;

    @Autowired
    private ProjectService svcProject;

    @Autowired
    private SprintService svcSprint;

    // === CONFIG ============================================================

    public Parameter getVersion(Request req, Response res) {
        res.type("application/json");
        String value = svcConfig.getVersion();
        if (value != null) {
            res.status(200);
        } else {
            res.status(204);
        }
        return new Parameter("version", value);
    }

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

        try {
            User usr = svcUser.add(new ObjectMapper<User>(User.class).parse(req.body()));
            res.status(201);
            res.header(LOCATION, "/api/users/" + usr.getId());
        } catch (CardioFunctionalException e) {
            res.status(400);
        } catch (CardioTechnicalException e) {
            res.status(500);
        }

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

        List<Sprint> response = null;

        Set<String> params = req.queryParams();
        if (params != null && params.contains("day")) {
            LOGGER.debug("filter on day : {}", req.queryParams("day"));
            response = svcSprint.findByDay(req.queryParams("day"));
        } else {
            response = svcSprint.all();
        }

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
            if (req.queryParams("desc") != null) {
                LOGGER.debug("reverse order");
                Collections.reverse(response);
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

    public SprintData getSprintData(Request req, Response res, String id) {
        res.type("application/json");
        SprintData data = svcSprint.findData(id);
        if (data != null) {
            res.status(200);
        } else {
            res.status(204);
        }
        return data;
    }

    public String createSprint(Request req, Response res) {
        String response = "OK";
        try {
            Sprint sprint = svcSprint.add(new ObjectMapper<Sprint>(Sprint.class).parse(req.body()));
            res.status(201);
            res.header(LOCATION, "/api/sprints/" + sprint.getId());
        } catch (CardioFunctionalException e) {
            res.status(400);
            response = e.getMessage();
        } catch (CardioTechnicalException e) {
            res.status(500);
            response = e.getMessage();
        }

        return response;
    }

    public String updateSprintProperties(Request req, Response res, String id) {
        String response = "OK";
        try {
            Sprint sprint = svcSprint.update(new ObjectMapper<Sprint>(Sprint.class).parse(req.body()));
            res.status(201);
            res.header(LOCATION, "/api/sprints/" + sprint.getId());
        } catch (CardioFunctionalException e) {
            res.status(400);
            response = e.getMessage();
        } catch (CardioTechnicalException e) {
            res.status(500);
            response = e.getMessage();
        }
        return response;
    }

    public String patchSprintProperties(Request req, Response res, String id) {
        String response = "OK";
        try {
            Sprint sprint = svcSprint.patch(new ObjectMapper<Sprint>(Sprint.class).parse(req.body()));
            res.status(201);
            res.header(LOCATION, "/api/sprints/" + sprint.getId());
        } catch (CardioFunctionalException e) {
            res.status(400);
            response = e.getMessage();
        } catch (CardioTechnicalException e) {
            res.status(500);
            response = e.getMessage();
        }
        return response;
    }

    public String updateSprintData(Request req, Response res, String id) {
        String response = "OK";
        try {
            Sprint sprint = svcSprint.find(id);
            if (sprint != null) {
                svcSprint.updateData(id, new ObjectMapper<SprintData>(SprintData.class).parse(req.body()));
                res.status(201);
                res.header(LOCATION, "/api/sprints/" + sprint.getId());
            }
        } catch (CardioFunctionalException e) {
            res.status(400);
            response = e.getMessage();
        } catch (CardioTechnicalException e) {
            res.status(500);
            response = e.getMessage();
        }
        return response;
    }

    public ProjectDataDetails getProjectData(Request req, Response res) {
        res.type("application/json");
        ProjectDataDetails chart = svcProject.projectData();
        if (chart != null) {
            res.status(200);
        } else {
            res.status(204);
        }
        return chart;
    }

    public ProjectVision getProjectVision(Request req, Response res) {
        res.type("application/json");
        ProjectVision vision = svcProject.getProjectVision();
        if (vision != null) {
            res.status(200);
        } else {
            res.status(204);
        }
        return vision;
    }

}
