package com.sopra.agile.cardio.ui.controller;

import java.util.HashMap;
import java.util.Map;

import spark.Request;

public class BaseController {

    protected static Map<String, Object> initAttributes(final Request req) {
        Map<String, Object> attributes = initMap();
        return attributes;
    }

    protected static Map<String, Object> initMap() {
        Map<String, Object> attributes = new HashMap<String, Object>();

        attributes.put("title", "cardio");

        attributes.put("menuSprintPlanning", "");
        attributes.put("menuSprints", "");
        attributes.put("menuUsers", "");

        return attributes;
    }
}
