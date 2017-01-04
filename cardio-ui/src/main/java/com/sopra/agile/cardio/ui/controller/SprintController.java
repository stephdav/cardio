package com.sopra.agile.cardio.ui.controller;

import com.sopra.agile.cardio.ui.utils.Path;

import spark.Request;
import spark.Response;
import spark.Route;

public class SprintController extends BaseController {
    public static final Route SPRINT = (Request req, Response res) -> {
        return renderPathWithMenu(req, res, Path.Template.SPRINT, "menuSprint");
    };
}
