package com.sopra.agile.cardio.config;

import static spark.Spark.before;
import static spark.Spark.delete;
import static spark.Spark.exception;
import static spark.Spark.get;
import static spark.Spark.post;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sopra.agile.cardio.controller.RestController;
import com.sopra.agile.cardio.utils.JsonTransformer;

public class RestConfig {

    private static final String PARAM_ID = ":id";

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

        // USERS
        get("/api/users", (req, res) -> controller.getAllUsers(req, res), new JsonTransformer());
        get("/api/users/" + PARAM_ID, (req, res) -> controller.getUser(req, res, req.params(PARAM_ID)),
                new JsonTransformer());
        post("/api/users", (req, res) -> controller.createUser(req, res));
        delete("/api/users/" + PARAM_ID, (req, res) -> controller.deleteUser(req, res, req.params(PARAM_ID)));

    }
}
