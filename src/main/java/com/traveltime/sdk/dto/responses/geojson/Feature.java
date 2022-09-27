package com.traveltime.sdk.dto.responses.geojson;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.geojson.GeoJsonObject;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
public class Feature<G extends GeoJsonObject, P> {
    @NonNull
    String type;

    @NonNull
    G geometry;

    P properties;
}
