package com.sopra.agile.cardio.ui;

import static spark.Spark.after;
import static spark.Spark.before;
import static spark.Spark.get;
import static spark.Spark.staticFiles;

import com.sopra.agile.cardio.ui.controller.IndexController;
import com.sopra.agile.cardio.ui.controller.UserController;
import com.sopra.agile.cardio.ui.utils.Filters;
import com.sopra.agile.cardio.ui.utils.Path;
import com.sopra.agile.cardio.ui.utils.ViewUtil;

public class UIConfig {

    public UIConfig() {
        // Assign folder for static files
        staticFiles.location("/public");
        staticFiles.expireTime(600L);

        // Set up before-filters (called before each get/post)
        before(Path.Web.BASE + "/*", Filters.addTrailingSlashes);

        get(Path.Web.INDEX, IndexController.home);
        get(Path.Web.USERS, UserController.users);
        get(Path.Web.BASE + "/*", ViewUtil.notFound);

        // Set up after-filters (called after each get/post)
        after(Path.Web.BASE + "/*", Filters.addGzipHeader);

    }

}
