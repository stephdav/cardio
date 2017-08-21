package com.sopra.agile.cardio.back.service;

import java.util.List;

import com.sopra.agile.cardio.common.exception.CardioTechnicalException;
import com.sopra.agile.cardio.common.model.Kanban;
import com.sopra.agile.cardio.common.model.ProjectDataDetails;
import com.sopra.agile.cardio.common.model.StoryMonitor;

public interface ProjectService {

    ProjectDataDetails projectData();

    Kanban getScrumBoard() throws CardioTechnicalException;

    List<StoryMonitor> getBurndown() throws CardioTechnicalException;

}
