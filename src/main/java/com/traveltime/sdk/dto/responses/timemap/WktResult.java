package com.traveltime.sdk.dto.responses.timemap;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.Polygon;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Jacksonized
@Builder
public class WktResult {
    String searchId;
    Geometry shape;
    ResponseProperties properties;
}
