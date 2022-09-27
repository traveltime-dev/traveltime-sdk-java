package com.traveltime.sdk.dto.responses.geojson;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
public class LineString implements Geometry {
    @NonNull
    List<Coordinates> coordinates;
}
