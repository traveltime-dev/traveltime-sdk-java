package com.traveltime.sdk.dto.responses.timemap;

import lombok.*;
import lombok.extern.jackson.Jacksonized;
import org.locationtech.jts.geom.Geometry;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
public class WktResult {
    String searchId;
    Geometry shape;
    ResponseProperties properties;
}
