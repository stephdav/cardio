package com.sopra.agile.cardio.back.rest;

import static spark.Spark.before;
import static spark.Spark.delete;
import static spark.Spark.exception;
import static spark.Spark.get;
import static spark.Spark.patch;
import static spark.Spark.post;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sopra.agile.cardio.back.controller.ProjectController;
import com.sopra.agile.cardio.back.controller.SprintController;
import com.sopra.agile.cardio.back.controller.StoryController;
import com.sopra.agile.cardio.back.controller.UserController;
import com.sopra.agile.cardio.back.utils.BootstrapTable;
import com.sopra.agile.cardio.back.utils.JsonTransformer;
import com.sopra.agile.cardio.common.model.Sprint;
import com.sopra.agile.cardio.common.model.Story;
import com.sopra.agile.cardio.common.model.User;

public class RestConfig {

    private static final String PARAM_ID = ":id";
    private static final String PARAM_KEY = ":key";

    private static final Logger LOGGER = LoggerFactory.getLogger(RestConfig.class);

    private ProjectController projectController;
    private UserController userController;
    private SprintController sprintController;
    private StoryController storyController;

    public RestConfig(ProjectController projectController, UserController userController,
            SprintController sprintController, StoryController storyController) {

        this.projectController = projectController;
        this.userController = userController;
        this.sprintController = sprintController;
        this.storyController = storyController;

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

        // PROJECT

        get("/api/version", (req, res) -> projectController.getVersion(req, res), new JsonTransformer());

        get("/api/config/parameters/:key",
                (req, res) -> projectController.getParameter(req, res, req.params(PARAM_KEY)), new JsonTransformer());

        get("/api/project/data", (req, res) -> projectController.getProjectData(req, res), new JsonTransformer());

        get("/api/project/kanban", (req, res) -> projectController.getKanban(req, res), new JsonTransformer());

        // USERS

        get("/api/users/count", (req, res) -> userController.getCount(req, res), new JsonTransformer());
        get("/api/users/:id", (req, res) -> userController.getUser(req, res, req.params(PARAM_ID)),
                new JsonTransformer());
        get("/api/users", (req, res) -> {
            List<User> list = userController.getAllUsers(req, res);
            if (req.queryParams("bootstrap") == null) {
                return list;
            } else {
                return new BootstrapTable<User>().convert(list, res);
            }
        } , new JsonTransformer());

        post("/api/users", (req, res) -> userController.createUser(req, res));
        delete("/api/users/:id", (req, res) -> userController.deleteUser(req, res, req.params(PARAM_ID)));

        // SPRINTS

        get("/api/sprints/count", (req, res) -> sprintController.getCount(req, res), new JsonTransformer());
        get("/api/sprints/:id/data", (req, res) -> sprintController.getSprintData(req, res, req.params(PARAM_ID)),
                new JsonTransformer());
        get("/api/sprints/:id", (req, res) -> sprintController.getSprint(req, res, req.params(PARAM_ID)),
                new JsonTransformer());

        get("/api/sprints", (req, res) -> {
            List<Sprint> list = sprintController.getAllSprints(req, res);
            if (req.queryParams("bootstrap") == null) {
                return list;
            } else {
                return new BootstrapTable<Sprint>().convert(list, res);
            }
        } , new JsonTransformer());

        post("/api/sprints/:id/data", (req, res) -> sprintController.updateSprintData(req, res, req.params(PARAM_ID)));
        post("/api/sprints/:id", (req, res) -> sprintController.updateSprintProperties(req, res, req.params(PARAM_ID)));
        post("/api/sprints", (req, res) -> sprintController.createSprint(req, res));

        patch("/api/sprints/:id", (req, res) -> sprintController.patchSprintProperties(req, res, req.params(PARAM_ID)));

        // STORIES

        get("/api/stories/count", (req, res) -> storyController.getCount(req, res), new JsonTransformer());
        get("/api/stories/:id", (req, res) -> storyController.getStory(req, res, req.params(PARAM_ID)),
                new JsonTransformer());
        get("/api/stories", (req, res) -> {
            List<Story> list = storyController.getAllStorys(req, res);
            if (req.queryParams("bootstrap") == null) {
                return list;
            } else {
                return new BootstrapTable<Story>().convert(list, res);
            }
        } , new JsonTransformer());

        post("/api/stories/:id", (req, res) -> storyController.updateStory(req, res, req.params(PARAM_ID)));
        post("/api/stories", (req, res) -> storyController.createStory(req, res));

        patch("/api/stories/:id", (req, res) -> storyController.patchStory(req, res, req.params(PARAM_ID)));
    }
}
