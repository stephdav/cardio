package com.sopra.agile.cardio.ui.controller;

import com.sopra.agile.cardio.ui.utils.Path;

import spark.Request;
import spark.Response;
import spark.Route;

public class ActivitiesController extends BaseController {
    public static final Route ACTIVITIES = (Request req, Response res) -> {
        return renderPathWithMenu(req, res, Path.Template.ACTIVITIES, "menuActivities");
    };
}
