package com.sopra.agile.cardio.back.service;

import com.sopra.agile.cardio.back.model.Parameter;
import com.sopra.agile.cardio.common.model.Chart;
import com.sopra.agile.cardio.common.model.Sprint;
import com.sopra.agile.cardio.common.model.SprintData;

public interface SprintService extends BaseService<Sprint> {

    Chart data(String id);

    Sprint update(Sprint sprint);

    Sprint currentSprint();

    Parameter leftDays();

    Chart burndown();

    Chart burnup();

    void updateData(SprintData data);
}
