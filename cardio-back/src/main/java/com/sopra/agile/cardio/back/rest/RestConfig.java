package com.sopra.agile.cardio.back.rest;

import static spark.Spark.before;
import static spark.Spark.delete;
import static spark.Spark.exception;
import static spark.Spark.get;
import static spark.Spark.post;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sopra.agile.cardio.back.controller.RestController;
import com.sopra.agile.cardio.back.utils.JsonTransformer;

public class RestConfig {

    private static final String PARAM_ID = ":id";
    private static final String PARAM_KEY = ":key";

    private static final Logger LOGGER = LoggerFactory.getLogger(RestConfig.class);

    private RestController controller;

    public RestConfig(RestController controller) {
        this.controller = controller;
        setupFilters();
        setupRoutes();
        exception(Exception.class, (exception, request, response) -> {
            exception.printStackTrace();
        });
    }

    private void setupFilters() {
        before("/api/*", (request, response) -> {
            StringBuffer sb = new StringBuffer();
            for (String param : request.queryParams()) {
                if (sb.length() == 0) {
                    sb.append("?");
                } else {
                    sb.append("&");
                }
                sb.append(param).append("=").append(request.queryParams(param));
            }
            LOGGER.debug("{} {}{}", request.requestMethod(), request.uri(), sb.toString());
        });
    }

    private void setupRoutes() {

        // PARAMETERS
        get("/api/config/parameters/" + PARAM_KEY,
                (req, res) -> controller.getParameter(req, res, req.params(PARAM_KEY)), new JsonTransformer());

        // CURRENT SPRINT

        get("/api/sprints/current/burnup", (req, res) -> controller.getBurnup(req, res), new JsonTransformer());
        get("/api/sprints/current/burndown", (req, res) -> controller.getCurrentBurndown(req, res),
                new JsonTransformer());
        get("/api/sprints/current/leftdays", (req, res) -> controller.getCurrentLeftDays(req, res),
                new JsonTransformer());
        get("/api/sprints/current", (req, res) -> controller.getCurrentSprint(req, res), new JsonTransformer());

        // SPRINTS

        get("/api/sprints/" + PARAM_ID + "/data",
                (req, res) -> controller.getSprintData(req, res, req.params(PARAM_ID)), new JsonTransformer());
        get("/api/sprints/" + PARAM_ID, (req, res) -> controller.getSprint(req, res, req.params(PARAM_ID)),
                new JsonTransformer());
        get("/api/sprints", (req, res) -> controller.getAllSprints(req, res), new JsonTransformer());

        post("/api/sprints/" + PARAM_ID + "/data",
                (req, res) -> controller.updateSprintData(req, res, req.params(PARAM_ID)));
        post("/api/sprints/" + PARAM_ID,
                (req, res) -> controller.updateSprintProperties(req, res, req.params(PARAM_ID)));
        post("/api/sprints", (req, res) -> controller.createSprint(req, res));

        // USERS
        get("/api/users", (req, res) -> controller.getAllUsers(req, res), new JsonTransformer());
        get("/api/users/" + PARAM_ID, (req, res) -> controller.getUser(req, res, req.params(PARAM_ID)),
                new JsonTransformer());
        post("/api/users", (req, res) -> controller.createUser(req, res));
        delete("/api/users/" + PARAM_ID, (req, res) -> controller.deleteUser(req, res, req.params(PARAM_ID)));
    }
}