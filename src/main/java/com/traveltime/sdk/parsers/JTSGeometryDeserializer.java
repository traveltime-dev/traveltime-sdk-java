package com.traveltime.sdk.parsers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;

import java.io.IOException;

public class JTSGeometryDeserializer extends StdDeserializer<Geometry> {

    private static WKTReader reader = new WKTReader();

    public JTSGeometryDeserializer() {
        this(null);
    }

    public JTSGeometryDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Geometry deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException {
        String result = jsonParser.getValueAsString();
        try {
            return reader.read(result);
        } catch (ParseException e) {
            throw new IOException(e.getMessage());
        }
    }
}
