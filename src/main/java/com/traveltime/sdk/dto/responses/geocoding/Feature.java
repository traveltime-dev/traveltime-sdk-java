package com.traveltime.sdk.dto.responses.geocoding;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.geojson.Point;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
public class Feature {

    @NonNull
    String type;

    @NonNull
    Point geometry;

    @NonNull
    Properties properties;
}
