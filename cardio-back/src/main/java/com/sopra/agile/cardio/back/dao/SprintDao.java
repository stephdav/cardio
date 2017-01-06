package com.sopra.agile.cardio.back.dao;

import java.util.List;

import com.sopra.agile.cardio.common.exception.CardioTechnicalException;
import com.sopra.agile.cardio.common.model.Sprint;

public interface SprintDao extends BaseDao<Sprint> {

    Sprint findByName(String name) throws CardioTechnicalException;

    Sprint update(Sprint sprint) throws CardioTechnicalException;

    Sprint current() throws CardioTechnicalException;

    List<Sprint> allCompleted() throws CardioTechnicalException;

}
