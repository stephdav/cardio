package com.sopra.agile.cardio.ui.controller;

import java.util.HashMap;
import java.util.Map;

import com.sopra.agile.cardio.ui.utils.ViewUtil;

import spark.Request;
import spark.Response;

public class BaseController {

    protected static String renderPathWithMenu(Request req, Response res, String path, String menu) {
        Map<String, Object> attributes = initAttributes(req);
        attributes.put(menu, "active");
        return ViewUtil.render(req, attributes, path);
    }

    protected static Map<String, Object> initAttributes(final Request req) {
        Map<String, Object> attributes = initMap();
        return attributes;
    }

    protected static Map<String, Object> initMap() {
        Map<String, Object> attributes = new HashMap<String, Object>();

        attributes.put("title", "cardio");

        attributes.put("menuAbout", "");
        attributes.put("menuKanban", "");
        attributes.put("menuSprintPlanning", "");
        attributes.put("menuSprint", "");
        attributes.put("menuSprints", "");
        attributes.put("menuStories", "");
        attributes.put("menuUsers", "");
        attributes.put("menuVision", "");

        return attributes;
    }
}
