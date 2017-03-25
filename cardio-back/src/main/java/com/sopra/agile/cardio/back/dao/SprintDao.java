package com.sopra.agile.cardio.back.dao;

import java.util.List;

import com.sopra.agile.cardio.common.exception.CardioTechnicalException;
import com.sopra.agile.cardio.common.model.Parameter;
import com.sopra.agile.cardio.common.model.Sprint;

public interface SprintDao extends BaseLongDao<Sprint> {

    Parameter count() throws CardioTechnicalException;

    Parameter countCompleted() throws CardioTechnicalException;

    Sprint findByName(String name) throws CardioTechnicalException;

    List<Sprint> findByDay(String day) throws CardioTechnicalException;

    Sprint update(Sprint sprint) throws CardioTechnicalException;

    List<Sprint> allCompleted() throws CardioTechnicalException;

    List<Sprint> overlaping(Sprint sprint) throws CardioTechnicalException;

}
