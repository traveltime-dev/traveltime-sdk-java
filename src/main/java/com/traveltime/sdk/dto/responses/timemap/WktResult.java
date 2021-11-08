package com.traveltime.sdk.dto.responses.timemap;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;
import org.locationtech.jts.geom.Geometry;

@Getter
@Jacksonized
@Builder
public class WktResult {
    String searchId;
    Geometry shape;
    ResponseProperties properties;
}
