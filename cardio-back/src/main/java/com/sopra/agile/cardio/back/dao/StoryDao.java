package com.sopra.agile.cardio.back.dao;

import java.util.List;

import com.sopra.agile.cardio.common.exception.CardioTechnicalException;
import com.sopra.agile.cardio.common.model.Parameter;
import com.sopra.agile.cardio.common.model.Story;
import com.sopra.agile.cardio.common.model.StoryMonitor;

public interface StoryDao extends BaseLongDao<Story> {

    List<Parameter> countByStatus() throws CardioTechnicalException;

    Story update(Story story, boolean updateTimestamp) throws CardioTechnicalException;

    List<Story> findByStatus(String status) throws CardioTechnicalException;

    List<Story> findBySprint(long sprintId) throws CardioTechnicalException;

    List<StoryMonitor> findMonitoredStories(long sprintId) throws CardioTechnicalException;
}
