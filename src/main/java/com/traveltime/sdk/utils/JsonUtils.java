package com.traveltime.sdk.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.traveltime.sdk.dto.responses.errors.IOError;
import com.traveltime.sdk.dto.responses.errors.JsonProcessingError;
import com.traveltime.sdk.dto.responses.errors.TravelTimeError;
import com.traveltime.sdk.parsers.JTSGeometrySerializer;
import com.traveltime.sdk.parsers.JTSGeometryDeserializer;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.val;
import org.locationtech.jts.geom.Geometry;

import static com.fasterxml.jackson.databind.PropertyNamingStrategies.SNAKE_CASE;

public class JsonUtils {
    private static final String IO_JSON_ERROR = "Something went wrong when parsing json response: ";

    private JsonUtils() {
        throw new IllegalStateException("Utility class");
    }

    private static SimpleModule module = new SimpleModule()
        .addSerializer(Geometry.class, new JTSGeometrySerializer())
        .addDeserializer(Geometry.class, new JTSGeometryDeserializer());

    private static JavaTimeModule timeModule = new JavaTimeModule();

    private static final ObjectMapper DEFAULT_MAPPER = new ObjectMapper()
        .registerModule(module)
        .registerModule(timeModule)
        .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
        .setPropertyNamingStrategy(SNAKE_CASE);

    public static <T> Either<TravelTimeError, T> fromJson(final String content, final Class<T> clazz) {
        return Try
            .of(() -> DEFAULT_MAPPER.readValue(content, clazz))
            .toEither()
            .mapLeft(cause -> new IOError(cause, IO_JSON_ERROR + cause.getMessage()));
    }

    public static boolean isJsonValid(final String content) {
        return Try.of(() -> DEFAULT_MAPPER.readTree(content)).isSuccess();
    }

    public static Either<TravelTimeError, String> toJsonPretty(final Object value) {
        return Try
            .of(() -> DEFAULT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(value))
            .toEither()
            .mapLeft(error -> new JsonProcessingError(error.getMessage()));
    }

    public static Either<TravelTimeError, String> toJson(final Object value) {
        return Try
            .of(() -> DEFAULT_MAPPER.writeValueAsString(value))
            .toEither()
            .mapLeft(error -> new JsonProcessingError(error.getMessage()));
    }
}
