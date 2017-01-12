package com.sopra.agile.cardio.back.service;

import java.util.List;

import com.sopra.agile.cardio.common.model.Sprint;
import com.sopra.agile.cardio.common.model.SprintData;

public interface SprintService extends BaseService<Sprint> {

    Sprint findByName(String name);

    List<Sprint> findByDay(String day);

    Sprint update(Sprint sprint);

    SprintData findData(String id);

    void updateData(String id, SprintData data);
}
