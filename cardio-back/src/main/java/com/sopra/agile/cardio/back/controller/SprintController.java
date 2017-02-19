package com.sopra.agile.cardio.back.controller;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.sopra.agile.cardio.back.model.ObjectMapper;
import com.sopra.agile.cardio.back.service.SprintService;
import com.sopra.agile.cardio.back.utils.Paginate;
import com.sopra.agile.cardio.common.exception.CardioFunctionalException;
import com.sopra.agile.cardio.common.exception.CardioTechnicalException;
import com.sopra.agile.cardio.common.model.Sprint;
import com.sopra.agile.cardio.common.model.SprintData;

import spark.Request;
import spark.Response;

@Controller
public class SprintController {

    private static final String LOCATION = "Location";

    private static final Logger LOGGER = LoggerFactory.getLogger(SprintController.class);

    @Autowired
    private SprintService svcSprint;

    public SprintController() {
        // Empty constructor
    }

    // === SPRINTS ===========================================================

    public List<Sprint> getAllSprints(Request req, Response res) {

        res.type("application/json");

        List<Sprint> response = null;
        try {
            List<Sprint> list = filterSprints(req);
            if (list != null) {
                sortSprints(list, req.queryParams("sortName"), req.queryParams("sortOrder"));
                response = new Paginate<Sprint>(Sprint.class).paginate(req, res, list);
            }
        } catch (CardioFunctionalException e) {
            res.status(400);
        } catch (CardioTechnicalException e) {
            res.status(500);
        }

        return response;
    }

    private List<Sprint> filterSprints(Request req) throws CardioTechnicalException, CardioFunctionalException {
        List<Sprint> list = null;
        Set<String> params = req.queryParams();
        if (params != null && params.contains("day")) {
            LOGGER.debug("filter on day : {}", req.queryParams("day"));
            list = svcSprint.findByDay(req.queryParams("day"));
        } else {
            list = svcSprint.all();
        }
        return list;
    }

    private void sortSprints(List<Sprint> response, String key, String sortOrder) {
        // sort results
        if (key != null) {
            LOGGER.debug("sort on '{}'", key);
            if ("name".equals(key)) {
                response.sort(Comparator.comparing(Sprint::getName));
            } else if ("startdate".equals(key)) {
                response.sort(Comparator.comparing(Sprint::getStartDate));
            } else if ("enddate".equals(key)) {
                response.sort(Comparator.comparing(Sprint::getEndDate));
            } else {
                LOGGER.debug("unknown sorting key {}", key);
                response.sort(Comparator.comparing(Sprint::getId));
            }
        } else {
            // default sorting
            response.sort(Comparator.comparing(Sprint::getId));
        }
        if ("desc".equals(sortOrder)) {
            LOGGER.debug("reverse order");
            Collections.reverse(response);
        }
    }

    public Sprint getSprint(Request req, Response res, String id) {
        res.type("application/json");
        Sprint sprint = null;
        try {
            sprint = svcSprint.find(id);
            if (sprint != null) {
                res.status(200);
            } else {
                res.status(204);
            }
        } catch (CardioFunctionalException e) {
            res.status(400);
        } catch (CardioTechnicalException e) {
            res.status(500);
        }

        return sprint;
    }

    public SprintData getSprintData(Request req, Response res, String id) {
        res.type("application/json");
        SprintData data = svcSprint.findData(id);
        if (data != null) {
            res.status(200);
        } else {
            res.status(204);
        }
        return data;
    }

    public String createSprint(Request req, Response res) {
        String response = "OK";
        try {
            Sprint sprint = svcSprint.add(new ObjectMapper<Sprint>(Sprint.class).parse(req.body()));
            res.status(201);
            res.header(LOCATION, "/api/sprints/" + sprint.getId());
        } catch (CardioFunctionalException e) {
            res.status(400);
            response = e.getMessage();
        } catch (CardioTechnicalException e) {
            res.status(500);
            response = e.getMessage();
        }

        return response;
    }

    public String updateSprintProperties(Request req, Response res, String id) {
        String response = "OK";
        try {
            Sprint sprint = svcSprint.update(new ObjectMapper<Sprint>(Sprint.class).parse(req.body()));
            res.status(201);
            res.header(LOCATION, "/api/sprints/" + sprint.getId());
        } catch (CardioFunctionalException e) {
            res.status(400);
            response = e.getMessage();
        } catch (CardioTechnicalException e) {
            res.status(500);
            response = e.getMessage();
        }
        return response;
    }

    public String patchSprintProperties(Request req, Response res, String id) {
        String response = "OK";
        try {
            Sprint sprint = svcSprint.patch(new ObjectMapper<Sprint>(Sprint.class).parse(req.body()));
            res.status(201);
            res.header(LOCATION, "/api/sprints/" + sprint.getId());
        } catch (CardioFunctionalException e) {
            res.status(400);
            response = e.getMessage();
        } catch (CardioTechnicalException e) {
            res.status(500);
            response = e.getMessage();
        }
        return response;
    }

    public String updateSprintData(Request req, Response res, String id) {
        String response = "OK";
        try {
            Sprint sprint = svcSprint.find(id);
            if (sprint != null) {
                svcSprint.updateData(id, new ObjectMapper<SprintData>(SprintData.class).parse(req.body()));
                res.status(201);
                res.header(LOCATION, "/api/sprints/" + sprint.getId());
            }
        } catch (CardioFunctionalException e) {
            res.status(400);
            response = e.getMessage();
        } catch (CardioTechnicalException e) {
            res.status(500);
            response = e.getMessage();
        }
        return response;
    }

}
