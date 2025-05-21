package com.stream.app.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Convert;

import java.nio.file.Path;
import java.nio.file.Paths;

@Convert
public class PathConverter implements AttributeConverter<Path, String> {

    @Override
    public String convertToDatabaseColumn(Path attribute) {
        return attribute == null ? "" : attribute.toString();
    }

    @Override
    public Path convertToEntityAttribute(String dbData) {
        return Paths.get(dbData);
    }
}
