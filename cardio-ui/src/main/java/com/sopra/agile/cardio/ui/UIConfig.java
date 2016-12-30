package com.sopra.agile.cardio.ui;

import static spark.Spark.after;
import static spark.Spark.before;
import static spark.Spark.get;
import static spark.Spark.redirect;
import static spark.Spark.staticFiles;

import com.sopra.agile.cardio.ui.controller.IndexController;
import com.sopra.agile.cardio.ui.controller.PlanningController;
import com.sopra.agile.cardio.ui.controller.SprintController;
import com.sopra.agile.cardio.ui.controller.SprintsController;
import com.sopra.agile.cardio.ui.controller.UsersController;
import com.sopra.agile.cardio.ui.utils.Filters;
import com.sopra.agile.cardio.ui.utils.Path;
import com.sopra.agile.cardio.ui.utils.ViewUtil;

public class UIConfig {

    public UIConfig() {
        // Assign folder for static files
        staticFiles.location("/public");
        staticFiles.expireTime(600L);

        // Redirect on index
        redirect.get("/", Path.Web.INDEX);

        // Set up before-filters (called before each get/post)
        before(Path.Web.ALL, Filters.ADD_TRAILING_SLASHES);

        //
        get(Path.Web.INDEX, IndexController.HOME);
        get(Path.Web.USERS, UsersController.USERS);
        get(Path.Web.SPRINTS, SprintsController.SPRINTS);
        get(Path.Web.PLANNING, PlanningController.PLANNING);
        get(Path.Web.SPRINT, SprintController.SPRINT);
        get(Path.Web.ALL, ViewUtil.NOT_FOUND);

        // Set up after-filters (called after each get/post)
        after(Path.Web.ALL, Filters.ADD_GZIP_HEADER);

    }

}
