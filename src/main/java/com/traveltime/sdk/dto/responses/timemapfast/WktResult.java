package com.traveltime.sdk.dto.responses.timemapfast;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.locationtech.jts.geom.Geometry;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class WktResult {
  String searchId;
  Geometry shape;
}
