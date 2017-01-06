package com.sopra.agile.cardio.back.service;

import com.sopra.agile.cardio.common.model.Chart;
import com.sopra.agile.cardio.common.model.Sprint;
import com.sopra.agile.cardio.common.model.SprintData;
import com.sopra.agile.cardio.common.model.VelocityData;

public interface SprintService extends BaseService<Sprint> {

    Sprint findByName(String name);

    Sprint update(Sprint sprint);

    Sprint currentSprint();

    SprintData findData(String id);

    Chart burnup();

    VelocityData velocity();

    void updateData(String id, SprintData data);
}
