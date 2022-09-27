package com.traveltime.sdk.dto.responses.geojson;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        property = "type",
        use = JsonTypeInfo.Id.NAME
)
@JsonSubTypes({@JsonSubTypes.Type(Point.class), @JsonSubTypes.Type(LineString.class), @JsonSubTypes.Type(Polygon.class), @JsonSubTypes.Type(MultiPolygon.class)})
@JsonInclude(JsonInclude.Include.NON_NULL)
public interface Geometry {
}
