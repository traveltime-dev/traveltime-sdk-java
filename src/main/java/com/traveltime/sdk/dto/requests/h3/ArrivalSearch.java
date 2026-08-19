package com.traveltime.sdk.dto.requests.h3;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.traveltime.sdk.dto.common.H3Coords;
import com.traveltime.sdk.dto.common.Snapping;
import com.traveltime.sdk.dto.common.transportation.Transportation;
import com.traveltime.sdk.dto.requests.timemap.Range;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import java.time.Instant;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArrivalSearch {
    @NonNull
    String id;

    @NonNull
    H3Coords coords;

    @Valid
    @NonNull
    Transportation transportation;

    @NonNull
    Instant arrivalTime;

    @NonNull
    @Positive(message = "travelTime should be positive")
    Integer travelTime;

    @Valid
    Range range;

    Snapping snapping;

    /**
     * When true (API default), the returned cells will not cover large nearby water bodies.
     * Set to false to allow cells over water bodies like large lakes, wide rivers, and seas.
     */
    Boolean removeWaterBodies;
}
