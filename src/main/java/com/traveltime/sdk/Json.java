package com.traveltime.sdk;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import static com.fasterxml.jackson.databind.PropertyNamingStrategy.SNAKE_CASE;

public class Json {

    private static final ObjectMapper DEFAULT_MAPPER = new ObjectMapper()
        .setPropertyNamingStrategy(SNAKE_CASE);

    public static <T> T fromJson(final String content, final Class<T> clazz) throws IOException {
        return DEFAULT_MAPPER.readValue(content, clazz);
    }

    public static String toJson(final Object value) throws JsonProcessingException {
        return DEFAULT_MAPPER.writeValueAsString(value);
    }
}
