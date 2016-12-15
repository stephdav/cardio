package com.sopra.agile.cardio.back.utils.converter;

import org.springframework.stereotype.Service;

import com.sopra.agile.cardio.back.model.DbSprint;
import com.sopra.agile.cardio.common.model.Sprint;

import ma.glasnost.orika.BoundMapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.converter.ConverterFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

@Service
public class Converter {

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

    public Sprint map(DbSprint sprint) {
        return mapSprint2Db.map(sprint);
    }

    public DbSprint map(Sprint sprint) {
        return mapDb2Sprint.map(sprint);
    }
}
