package com.sopra.agile.cardio.back.rest;

import static spark.Spark.before;
import static spark.Spark.delete;
import static spark.Spark.exception;
import static spark.Spark.get;
import static spark.Spark.patch;
import static spark.Spark.path;
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

        path("/api/project", () -> {
            get("/data", (req, res) -> projectController.getProjectData(req, res), new JsonTransformer());
            get("/kanban", (req, res) -> projectController.getKanban(req, res), new JsonTransformer());
        });

        // === USERS ===

        path("/api/users", () -> {
            get("/export", (req, res) -> userController.exportUsers(req, res));
            get("/count", (req, res) -> userController.getCount(req, res), new JsonTransformer());
            get("/:id", (req, res) -> userController.getUser(req, res, req.params(PARAM_ID)), new JsonTransformer());
            get("", (req, res) -> {
                List<User> list = userController.getAllUsers(req, res);
                if (req.queryParams("bootstrap") == null) {
                    return list;
                }
                return new BootstrapTable<User>().convert(list, res);
            } , new JsonTransformer());

            post("/:id", (req, res) -> userController.updateUser(req, res));
            post("", (req, res) -> userController.createUser(req, res));

            delete("/:id", (req, res) -> userController.deleteUser(req, res, req.params(PARAM_ID)));
        });

        // === SPRINTS ===

        path("/api/sprints", () -> {
            get("/export", (req, res) -> sprintController.exportSprints(req, res));
            get("/count", (req, res) -> sprintController.getCount(req, res), new JsonTransformer());
            get("/:id/data", (req, res) -> sprintController.getSprintData(req, res, req.params(PARAM_ID)),
                    new JsonTransformer());
            get("/:id", (req, res) -> sprintController.getSprint(req, res, req.params(PARAM_ID)),
                    new JsonTransformer());

            get("", (req, res) -> {
                List<Sprint> list = sprintController.getAllSprints(req, res);
                if (req.queryParams("bootstrap") == null) {
                    return list;
                }
                return new BootstrapTable<Sprint>().convert(list, res);
            } , new JsonTransformer());

            post("/:id/data", (req, res) -> sprintController.updateSprintData(req, res, req.params(PARAM_ID)));
            post("/:id", (req, res) -> sprintController.updateSprintProperties(req, res, req.params(PARAM_ID)));
            post("", (req, res) -> sprintController.createSprint(req, res));

            patch("/:id", (req, res) -> sprintController.patchSprintProperties(req, res, req.params(PARAM_ID)));
        });

        // === STORIES ===

        path("/api/stories", () -> {

            get("/export", (req, res) -> storyController.exportStories(req, res));
            get("/count", (req, res) -> storyController.getCount(req, res), new JsonTransformer());
            get("/:id", (req, res) -> storyController.getStory(req, res, req.params(PARAM_ID)), new JsonTransformer());
            get("", (req, res) -> {
                List<Story> list = storyController.getAllStorys(req, res);
                if (req.queryParams("bootstrap") == null) {
                    return list;
                }
                return new BootstrapTable<Story>().convert(list, res);
            } , new JsonTransformer());

            post("/:id", (req, res) -> storyController.updateStory(req, res, req.params(PARAM_ID)));
            post("", (req, res) -> storyController.createStory(req, res));

            patch("/:id", (req, res) -> storyController.patchStoryField(req, res, req.params(PARAM_ID)));

            delete("/:id", (req, res) -> storyController.deleteStory(req, res, req.params(PARAM_ID)));
        });

    }
}
