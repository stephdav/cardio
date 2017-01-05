package com.sopra.agile.cardio.ui.controller;

import com.sopra.agile.cardio.ui.utils.Path;

import spark.Request;
import spark.Response;
import spark.Route;

public class AboutController extends BaseController {
    public static final Route ABOUT = (Request req, Response res) -> {
        return renderPathWithMenu(req, res, Path.Template.ABOUT, "menuAbout");
    };
}
