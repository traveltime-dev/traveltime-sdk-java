package com.traveltime.sdk.dto.requests.h3;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.traveltime.sdk.dto.common.H3Coords;
import com.traveltime.sdk.dto.common.Snapping;
import com.traveltime.sdk.dto.common.transportationfast.Transportation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FastSearch {
    @NonNull
    String id;

    @NonNull
    H3Coords coords;

    @Valid
    @NonNull
    Transportation transportation;

    @NonNull
    String arrivalTimePeriod;

    @NonNull
    @Positive(message = "travelTime should be positive")
    Integer travelTime;

    Snapping snapping;

    /**
     * When true (API default), the returned cells will not cover large nearby water bodies.
     * Set to false to allow cells over water bodies like large lakes, wide rivers, and seas.
     */
    Boolean removeWaterBodies;
}
