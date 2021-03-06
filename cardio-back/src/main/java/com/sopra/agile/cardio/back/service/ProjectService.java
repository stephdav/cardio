package com.sopra.agile.cardio.back.service;

import com.sopra.agile.cardio.common.exception.CardioTechnicalException;
import com.sopra.agile.cardio.common.model.Kanban;
import com.sopra.agile.cardio.common.model.ProjectDataDetails;

public interface ProjectService {

    ProjectDataDetails projectData();

    Kanban getScrumBoard() throws CardioTechnicalException;

}
