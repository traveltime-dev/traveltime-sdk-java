package com.traveltime.sdk.dto.responses.geojson;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
public class Point implements Geometry {
    @NonNull
    Coordinates coordinates;
}
