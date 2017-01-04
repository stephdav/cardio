package com.sopra.agile.cardio.ui.controller;

import com.sopra.agile.cardio.ui.utils.Path;

import spark.Request;
import spark.Response;
import spark.Route;

public class IndexController extends BaseController {
    public static final Route HOME = (Request req, Response res) -> {
        return renderPathWithMenu(req, res, Path.Template.INDEX, "menuDashboard");
    };
}
