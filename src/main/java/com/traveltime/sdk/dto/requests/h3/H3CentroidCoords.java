package com.traveltime.sdk.dto.requests.h3;

import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
public class H3CentroidCoords implements Coords {
    /**
     * Index of an H3 cell, for example <tt>87194ad14ffffff</tt>.
     */
    @NonNull
    String h3Centroid;
}
