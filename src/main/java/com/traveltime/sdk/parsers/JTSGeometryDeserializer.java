package com.traveltime.sdk.parsers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.io.WKTReader;
import lombok.SneakyThrows;

import java.io.IOException;

public class JTSGeometryDeserializer extends StdDeserializer<Geometry> {

    private static WKTReader reader = new WKTReader();

    public JTSGeometryDeserializer() {
        this(null);
    }

    public JTSGeometryDeserializer(Class<?> vc) {
        super(vc);
    }

    @SneakyThrows
    @Override
    public Geometry deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException, JsonProcessingException {
        String result = jsonParser.getValueAsString();
        return reader.read(result);
    }
}
