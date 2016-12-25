package com.sopra.agile.cardio.ui.controller;

import java.util.Map;

import spark.Request;
import spark.Response;
import spark.Route;

import com.sopra.agile.cardio.ui.utils.Path;
import com.sopra.agile.cardio.ui.utils.ViewUtil;

public class SprintController extends BaseController {
	public static final Route SPRINT = (Request req, Response res) -> {
		Map<String, Object> attributes = initAttributes(req);
		return ViewUtil.render(req, attributes, Path.Template.SPRINT);
	};
}
