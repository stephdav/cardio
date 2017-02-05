package com.sopra.agile.cardio.back.service;

import com.sopra.agile.cardio.common.model.ProjectDataDetails;
import com.sopra.agile.cardio.common.model.ProjectVision;

public interface ProjectService {

    ProjectDataDetails projectData();

    ProjectVision getProjectVision();

}
