package com.traveltime.sdk.dto.requests.timemapfast;

import com.traveltime.sdk.dto.common.*;
import com.traveltime.sdk.dto.common.levelofdetail.LevelOfDetail;
import com.traveltime.sdk.dto.common.transportationfast.Transportation;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
public class Search {
    @NonNull
    String id;

    @NonNull
    Coordinates coords;

    @NonNull
    String arrivalTimePeriod;

    @NonNull
    Integer travelTime;

    @NonNull
    Transportation transportation;

    LevelOfDetail levelOfDetail;

    Snapping snapping;

    RenderMode renderMode;

    Integer bufferDistance;

    /**
     * When true (API default), the returned shape will not cover large nearby water bodies.
     * Set to false to allow the shape to cover water bodies like large lakes, wide rivers, and seas.
     */
    Boolean removeWaterBodies;
}
