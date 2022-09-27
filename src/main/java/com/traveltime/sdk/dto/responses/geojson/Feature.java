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
public class Feature<G extends Geometry, P> {
    @NonNull
    String type;

    @NonNull
    G geometry;

    P properties;
}
