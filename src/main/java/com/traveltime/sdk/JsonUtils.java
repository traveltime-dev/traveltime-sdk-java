package com.traveltime.sdk;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.traveltime.sdk.parsers.JTSGeometrySerializer;
import com.traveltime.sdk.parsers.JTSGeometryDeserializer;
import com.vividsolutions.jts.geom.Geometry;

import java.io.IOException;

import static com.fasterxml.jackson.databind.PropertyNamingStrategy.SNAKE_CASE;

public class JsonUtils {

    private JsonUtils() {
        throw new IllegalStateException("Utility class");
    }

    private static SimpleModule module = new SimpleModule()
        .addSerializer(Geometry.class, new JTSGeometrySerializer())
        .addDeserializer(Geometry.class, new JTSGeometryDeserializer());

    private static final ObjectMapper DEFAULT_MAPPER = new ObjectMapper()
        .registerModule(module)
        .setPropertyNamingStrategy(SNAKE_CASE);

    public static <T> T fromJson(final String content, final Class<T> clazz) throws IOException {
        return DEFAULT_MAPPER.readValue(content, clazz);
    }


    public static String toJsonPretty(final Object value) throws JsonProcessingException {
        return DEFAULT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(value);
    }

    public static String toJson(final Object value) throws JsonProcessingException {
        return DEFAULT_MAPPER.writeValueAsString(value);
    }
}
