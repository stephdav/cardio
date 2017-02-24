package com.sopra.agile.cardio.back.dao;

import com.sopra.agile.cardio.common.exception.CardioTechnicalException;
import com.sopra.agile.cardio.common.model.Story;

public interface StoryDao extends BaseLongDao<Story> {

    Story update(Story story, boolean updateTimestamp) throws CardioTechnicalException;

}
