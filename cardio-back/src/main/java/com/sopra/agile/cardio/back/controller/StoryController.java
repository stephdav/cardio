package com.sopra.agile.cardio.back.controller;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.sopra.agile.cardio.back.model.ObjectMapper;
import com.sopra.agile.cardio.back.service.StoryService;
import com.sopra.agile.cardio.back.utils.Paginate;
import com.sopra.agile.cardio.common.exception.CardioFunctionalException;
import com.sopra.agile.cardio.common.exception.CardioTechnicalException;
import com.sopra.agile.cardio.common.model.Parameter;
import com.sopra.agile.cardio.common.model.Story;
import com.sopra.agile.cardio.common.model.StoryStatus;

import spark.Request;
import spark.Response;

@Controller
public class StoryController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(StoryController.class);

    @Autowired
    private StoryService svcStory;

    public StoryController() {
        // Empty constructor
    }

    // === USERS =============================================================

    public List<Parameter> getCount(Request req, Response res) {
        res.type("application/json");
        List<Parameter> response = null;

        try {
            response = svcStory.countByStatus();
            res.status(200);
        } catch (CardioTechnicalException e) {
            res.status(500);
        }

        return response;
    }

    public List<Story> getAllStorys(Request req, Response res) {
        res.type("application/json");
        List<Story> response = null;

        try {
            List<Story> list = svcStory.all();
            if (list != null) {
                list = filterStories(list, req);
                sortStories(list, req.queryParams("sortName"), req.queryParams("sortOrder"));
                response = new Paginate<Story>(Story.class).paginate(req, res, list);
            }
        } catch (CardioFunctionalException e) {
            res.status(400);
        } catch (CardioTechnicalException e) {
            res.status(500);
        }

        return response;
    }

    public Story getStory(Request req, Response res, String id) {
        res.type("application/json");
        Story story = null;

        try {
            story = svcStory.find(id);
            if (story != null) {
                res.status(200);
            } else {
                res.status(204);
            }
        } catch (CardioFunctionalException e) {
            res.status(400);
        } catch (CardioTechnicalException e) {
            res.status(500);
        }
        return story;
    }

    public String createStory(Request req, Response res) {
        String response = "OK";
        try {
            Story story = svcStory.add(new ObjectMapper<Story>(Story.class).parse(req.body()));
            res.status(201);
            res.header(LOCATION, "/api/stories/" + story.getId());
        } catch (CardioFunctionalException e) {
            res.status(400);
            response = e.getMessage();
        } catch (CardioTechnicalException e) {
            res.status(500);
            response = e.getMessage();
        }
        return response;
    }

    public String updateStory(Request req, Response res, String id) {
        String response = "OK";
        try {
            Story story = svcStory.update(new ObjectMapper<Story>(Story.class).parse(req.body()));
            res.status(201);
            res.header(LOCATION, "/api/stories/" + story.getId());
        } catch (CardioFunctionalException e) {
            res.status(400);
            response = e.getMessage();
        } catch (CardioTechnicalException e) {
            res.status(500);
            response = e.getMessage();
        }
        return response;
    }

    public String patchStory(Request req, Response res, String id) {
        String response = "OK";
        try {
            Story story = svcStory.patch(new ObjectMapper<Story>(Story.class).parse(req.body()));
            res.status(201);
            res.header(LOCATION, "/api/stories/" + story.getId());
        } catch (CardioFunctionalException e) {
            res.status(400);
            response = e.getMessage();
        } catch (CardioTechnicalException e) {
            res.status(500);
            response = e.getMessage();
        }
        return response;
    }

    public String patchStoryField(Request req, Response res, String id)
            throws CardioTechnicalException, CardioFunctionalException {
        String response = "OK";
        try {
            Story story = svcStory.patch(id, req.queryParams("status"), req.queryParams("contribution"),
                    req.queryParams("estimate"), req.queryParams("assignedUser"));
            res.status(201);
            res.header(LOCATION, "/api/stories/" + story.getId());
        } catch (CardioFunctionalException e) {
            res.status(400);
            response = e.getMessage();
        } catch (CardioTechnicalException e) {
            res.status(500);
            response = e.getMessage();
        }
        return response;
    }

    private List<Story> filterStories(List<Story> list, Request req) {
        if (req.queryParams("status") != null) {
            String filter = req.queryParams("status");
            LOGGER.debug("filter on status '{}'", filter);
            StoryStatus status = StoryStatus.valueOf(filter);
            list = list.stream().filter(line -> line.getStatus() == status).collect(Collectors.toList());
        }
        return list;
    }

    private void sortStories(List<Story> response, String key, String sortOrder) {
        // sort results
        if (key != null) {
            LOGGER.debug("sort=" + key);
            if ("status".equals(key)) {
                response.sort(Comparator.comparing(Story::getStatus));
            } else if ("contribution".equals(key)) {
                response.sort(Comparator.comparing(Story::getContribution));
            } else if ("estimate".equals(key)) {
                response.sort(Comparator.comparing(Story::getEstimate));
            } else {
                LOGGER.debug("unknown sorting key {}", key);
                response.sort(Comparator.comparing(Story::getId));
            }
        } else {
            // default sorting
            response.sort(Comparator.comparing(Story::getId));
        }
        if ("desc".equals(sortOrder)) {
            LOGGER.debug("reverse order");
            Collections.reverse(response);
        }
    }
}
