package com.sopra.agile.cardio.back.service;

import com.sopra.agile.cardio.back.model.Parameter;
import com.sopra.agile.cardio.common.model.Chart;
import com.sopra.agile.cardio.common.model.Sprint;
import com.sopra.agile.cardio.common.model.SprintData;

public interface SprintService extends BaseService<Sprint> {
    Sprint update(Sprint sprint);

    Sprint currentSprint();

    Parameter leftDays();

    Chart burndown();

    void updateData(SprintData data);
}
