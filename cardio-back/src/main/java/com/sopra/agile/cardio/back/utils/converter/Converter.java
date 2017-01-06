package com.sopra.agile.cardio.back.utils.converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.sopra.agile.cardio.back.model.DbSprint;
import com.sopra.agile.cardio.common.exception.CardioTechnicalException;
import com.sopra.agile.cardio.common.model.Sprint;

import ma.glasnost.orika.BoundMapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.converter.ConverterFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

@Service
public class Converter {

    private static final Logger LOGGER = LoggerFactory.getLogger(Converter.class);

    private MapperFactory mapperFactory;
    private BoundMapperFacade<DbSprint, Sprint> mapSprint2Db;
    private BoundMapperFacade<Sprint, DbSprint> mapDb2Sprint;

    public Converter() {
        mapperFactory = new DefaultMapperFactory.Builder().build();
        mapperFactory.classMap(DbSprint.class, Sprint.class).byDefault().register();

        ConverterFactory converterFactory = mapperFactory.getConverterFactory();
        converterFactory.registerConverter(new LocalDateStringConverter());

        mapSprint2Db = mapperFactory.getMapperFacade(DbSprint.class, Sprint.class);
        mapDb2Sprint = mapperFactory.getMapperFacade(Sprint.class, DbSprint.class);
    }

    public Sprint map(DbSprint sprint) throws CardioTechnicalException {
        Sprint response = null;
        try {
            response = mapSprint2Db.map(sprint);
        } catch (Exception ex) {
            throw new CardioTechnicalException("Failure when converting Sprint into DbSprint", ex);
        }
        return response;
    }

    public DbSprint map(Sprint sprint) throws CardioTechnicalException {
        DbSprint response = null;
        try {
            response = mapDb2Sprint.map(sprint);
        } catch (Exception ex) {
            throw new CardioTechnicalException("Failure when converting DbSprint into Sprint", ex);
        }
        return response;
    }
}
