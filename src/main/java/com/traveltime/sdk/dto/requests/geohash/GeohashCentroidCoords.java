package com.traveltime.sdk.dto.requests.geohash;

import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
public class GeohashCentroidCoords implements Coords {
    /**
     * Index of a geohash cell, for example <tt>gcpuv5</tt>.
     */
    @NonNull
    String geohashCentroid;
}
