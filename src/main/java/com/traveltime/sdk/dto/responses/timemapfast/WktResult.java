package com.traveltime.sdk.dto.responses.timemapfast;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.locationtech.jts.geom.Geometry;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
public class WktResult {
    String searchId;
    Geometry shape;
}
