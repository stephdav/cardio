package com.sopra.agile.cardio.ui.controller;

import java.util.Map;

import com.sopra.agile.cardio.ui.utils.Path;
import com.sopra.agile.cardio.ui.utils.ViewUtil;

import spark.Request;
import spark.Response;
import spark.Route;

public class IndexController extends BaseController {
    public static Route home = (Request req, Response res) -> {
        Map<String, Object> attributes = initAttributes(req);
        return ViewUtil.render(req, attributes, Path.Template.INDEX);
    };
}
