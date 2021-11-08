package com.traveltime.sdk.parsers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.io.WKTWriter;

import java.io.IOException;

public class JTSGeometrySerializer extends StdSerializer<Geometry> {

    private static WKTWriter writer = new WKTWriter();

    public JTSGeometrySerializer() {
        this(null);
    }

    public JTSGeometrySerializer(Class<Geometry> t) {
        super(t);
    }

    @Override
    public void serialize(Geometry value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeString(writer.write(value));
    }
}
