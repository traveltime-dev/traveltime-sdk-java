package com.traveltime.sdk.dto.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
public class GeohashCentroidCoords implements GeohashCoords {
    /**
     * Index of a geohash cell, for example <tt>gcpuv5</tt>.
     */
    @NonNull
    @JsonProperty("geohash_centroid")
    String geohashCentroid;
}
