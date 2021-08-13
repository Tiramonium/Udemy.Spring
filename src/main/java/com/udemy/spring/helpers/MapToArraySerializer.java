package com.udemy.spring.helpers;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.Map.Entry;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class MapToArraySerializer extends JsonSerializer<Map<?, ?>> {
    @Override
    public void serialize(Map<?, ?> prop, JsonGenerator generator, SerializerProvider serializer) throws IOException {
        generator.writeStartArray();

        for (Entry<?, ?> entry : prop.entrySet()) {
            generator.writeStartObject();

            if (entry.getKey() != null && !Objects.equals(entry.getValue(), "")) {
                generator.writeObjectField(entry.getKey().toString(), entry.getValue());
            } else {
                generator.writeObjectField("key", entry.getKey());
                generator.writeObjectField("value", entry.getValue());
            }

            generator.writeEndObject();
        }

        generator.writeEndArray();
    }
}