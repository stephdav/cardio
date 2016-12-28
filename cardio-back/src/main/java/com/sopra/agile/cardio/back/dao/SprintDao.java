package com.sopra.agile.cardio.back.dao;

import java.util.List;

import com.sopra.agile.cardio.common.model.Sprint;

public interface SprintDao extends BaseDao<Sprint> {
    Sprint update(Sprint sprint);

    Sprint current();

    List<Sprint> allCompleted();

}
