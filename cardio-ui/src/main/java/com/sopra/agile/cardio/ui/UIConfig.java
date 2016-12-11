package com.sopra.agile.cardio.ui;

import static spark.Spark.before;
import static spark.Spark.staticFiles;

import com.sopra.agile.cardio.ui.utils.Filters;

public class UIConfig {

    public UIConfig() {
        // Assign folder for static files
        staticFiles.location("/public");
        staticFiles.expireTime(600L);

        // Set up before-filters (called before each get/post)
        before("*", Filters.addTrailingSlashes);
    }

}
