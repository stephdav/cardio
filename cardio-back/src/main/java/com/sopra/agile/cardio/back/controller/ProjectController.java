package com.sopra.agile.cardio.back.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.sopra.agile.cardio.back.service.ConfigService;
import com.sopra.agile.cardio.back.service.ProjectService;
import com.sopra.agile.cardio.common.exception.CardioTechnicalException;
import com.sopra.agile.cardio.common.model.Kanban;
import com.sopra.agile.cardio.common.model.Parameter;
import com.sopra.agile.cardio.common.model.ProjectDataDetails;
import com.sopra.agile.cardio.common.model.StoryMonitor;

import spark.Request;
import spark.Response;

@Controller
public class ProjectController extends BaseController {

    @Autowired
    private ConfigService svcConfig;

    @Autowired
    private ProjectService svcProject;

    public ProjectController() {
        // Empty constructor
    }

    // === CONFIG ============================================================

    public Parameter getVersion(Request req, Response res) {
        res.type("application/json");
        String value = svcConfig.getVersion();
        if (value != null) {
            res.status(200);
        } else {
            res.status(204);
        }
        return new Parameter("version", value);
    }

    public Parameter getParameter(Request req, Response res, String key) {
        res.type("application/json");
        String value = svcConfig.getProperty(key);
        if (value != null) {
            res.status(200);
        } else {
            res.status(204);
        }
        return new Parameter(key, value);
    }

    // === PROJECT ===========================================================

    public ProjectDataDetails getProjectData(Request req, Response res) {
        res.type("application/json");
        ProjectDataDetails chart = svcProject.projectData();
        if (chart != null) {
            res.status(200);
        } else {
            res.status(204);
        }
        return chart;
    }

    // === KANBAN ===========================================================

    public Kanban getKanban(Request req, Response res) {
        res.type("application/json");
        Kanban kanban = null;
        try {
            kanban = svcProject.getScrumBoard();
            // } catch (CardioFunctionalException e) {
            // res.status(400);
        } catch (CardioTechnicalException e) {
            e.printStackTrace();
            res.status(500);
        }

        return kanban;
    }

    // === BURNDOWN ==========================================================

    public List<StoryMonitor> getBurndown(Request req, Response res) {
        res.type("application/json");
        List<StoryMonitor> kanban = null;
        try {
            kanban = svcProject.getBurndown();
        } catch (CardioTechnicalException e) {
            e.printStackTrace();
            res.status(500);
        }

        return kanban;
    }
}
