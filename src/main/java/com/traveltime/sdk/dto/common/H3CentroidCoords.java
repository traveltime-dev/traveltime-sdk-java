package com.traveltime.sdk.dto.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
public class H3CentroidCoords implements H3Coords {
    /**
     * Index of an H3 cell, for example <tt>87194ad14ffffff</tt>.
     */
    @NonNull
    @JsonProperty("h3_centroid")
    String h3Centroid;
}
