package com.sopra.agile.cardio.ui.controller;

import java.util.Map;

import com.sopra.agile.cardio.ui.utils.Path;
import com.sopra.agile.cardio.ui.utils.ViewUtil;

import spark.Request;
import spark.Response;
import spark.Route;

public class PlanningController extends BaseController {
    public static final Route PLANNING = (Request req, Response res) -> {
        Map<String, Object> attributes = initAttributes(req);
        attributes.put("menuSprintPlanning", "active");
        return ViewUtil.render(req, attributes, Path.Template.PLANNING);
    };
}