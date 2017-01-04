package com.sopra.agile.cardio.ui.controller;

import com.sopra.agile.cardio.ui.utils.Path;

import spark.Request;
import spark.Response;
import spark.Route;

public class UsersController extends BaseController {
    public static final Route USERS = (Request req, Response res) -> {
        return renderPathWithMenu(req, res, Path.Template.USERS, "menuUsers");
    };
}
