package com.traveltime.sdk.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.traveltime.sdk.dto.common.Coordinates;
import com.traveltime.sdk.dto.common.Coords;
import com.traveltime.sdk.dto.common.GeohashCentroidCoords;
import com.traveltime.sdk.dto.common.H3CentroidCoords;
import java.io.IOException;

public class GeneralCoordsDeserializer extends JsonDeserializer<Coords> {
    @Override
    public Coords deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        JsonNode node = parser.readValueAsTree();
        Class<? extends Coords> target = node.has("h3_centroid")
                ? H3CentroidCoords.class
                : node.has("geohash_centroid") ? GeohashCentroidCoords.class : Coordinates.class;
        return parser.getCodec().treeToValue(node, target);
    }
}
