package com.stream.app.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Convert;

import java.time.Instant;

@Convert
public class InstantTimeNow implements AttributeConverter<Instant, String> {


    @Override
    public String convertToDatabaseColumn(Instant attribute) {
        return Instant.now().toString();
    }

    @Override
    public Instant convertToEntityAttribute(String dbData) {
        return Instant.parse(dbData);
    }
}
