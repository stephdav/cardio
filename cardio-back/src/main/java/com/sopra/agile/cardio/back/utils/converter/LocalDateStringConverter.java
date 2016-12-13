package com.sopra.agile.cardio.back.utils.converter;

import org.joda.time.LocalDate;

import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.metadata.Type;

public class LocalDateStringConverter extends BidirectionalConverter<LocalDate, String> {

    public String convertTo(LocalDate source, Type<String> destinationType) {
        String value = null;
        if (source != null) {
            value = source.toString();
        }
        return value;
    }

    public LocalDate convertFrom(String source, Type<LocalDate> destinationType) {
        LocalDate value = null;
        if (source != null) {
            value = new LocalDate(source);
        }
        return value;
    }
}