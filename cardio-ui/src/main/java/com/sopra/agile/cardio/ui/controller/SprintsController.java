package com.sopra.agile.cardio.ui.controller;

import java.util.Map;

import com.sopra.agile.cardio.ui.utils.Path;
import com.sopra.agile.cardio.ui.utils.ViewUtil;

import spark.Request;
import spark.Response;
import spark.Route;

public class SprintsController extends BaseController {
    public static final Route SPRINTS = (Request req, Response res) -> {
        Map<String, Object> attributes = initAttributes(req);
        attributes.put("menuSprints", "active");
        return ViewUtil.render(req, attributes, Path.Template.SPRINTS);
    };
}
