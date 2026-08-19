package com.traveltime.sdk.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;
import lombok.AllArgsConstructor;

/**
 * The API takes a search location as either a lat/lng pair or a cell index, with nothing in the
 * payload to tell them apart, so the shape of the object decides.
 */
@AllArgsConstructor
public class CoordsDeserializer<T> extends JsonDeserializer<T> {
    private final String centroidField;
    private final Class<? extends T> centroidType;
    private final Class<? extends T> latLngType;

    @Override
    public T deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        JsonNode node = parser.readValueAsTree();
        Class<? extends T> target = node.has(centroidField) ? centroidType : latLngType;
        return parser.getCodec().treeToValue(node, target);
    }
}
