package com.sopra.agile.cardio.back.dao;

import java.util.List;

import com.sopra.agile.cardio.common.model.SprintDay;

public interface SprintDayDao extends BaseDao<SprintDay> {
    List<SprintDay> findBetween(String start, String end);

    SprintDay insertOrUpdate(SprintDay entity);
}
