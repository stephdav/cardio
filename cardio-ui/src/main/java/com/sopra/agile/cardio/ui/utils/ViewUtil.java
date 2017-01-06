package com.sopra.agile.cardio.ui.utils;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jetty.http.HttpStatus;

import com.sopra.agile.cardio.ui.pebble.FixedPebbleEngine;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;

public class ViewUtil {

    public static String render(Request request, Map<String, Object> model, String templatePath) {
        model.put("webPath", Path.Web.class);
        return strictPebbleEngine().render(new ModelAndView(model, templatePath));
    }

    public static final Route NOT_FOUND = (Request request, Response response) -> {
        response.status(HttpStatus.NOT_FOUND_404);
        return render(request, new HashMap<>(), Path.Template.NOT_FOUND);
    };

    private static FixedPebbleEngine strictPebbleEngine() {
        return new FixedPebbleEngine();
    }
}