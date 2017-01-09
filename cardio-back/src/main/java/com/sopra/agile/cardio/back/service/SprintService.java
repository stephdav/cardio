package com.sopra.agile.cardio.back.service;

import com.sopra.agile.cardio.common.model.Sprint;
import com.sopra.agile.cardio.common.model.SprintData;

public interface SprintService extends BaseService<Sprint> {

    Sprint findByName(String name);

    Sprint update(Sprint sprint);

    Sprint currentSprint();

    SprintData findData(String id);

    void updateData(String id, SprintData data);
}
