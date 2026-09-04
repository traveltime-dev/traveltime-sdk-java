package com.traveltime.sdk.dto.requests.timemapfast;

import com.traveltime.sdk.dto.common.Coordinates;
import com.traveltime.sdk.dto.common.PolygonsFilter;
import com.traveltime.sdk.dto.common.RenderMode;
import com.traveltime.sdk.dto.common.Snapping;
import com.traveltime.sdk.dto.common.levelofdetail.LevelOfDetail;
import com.traveltime.sdk.dto.common.transportationfast.Transportation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
public class OneToMany {
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

    @Valid
    PolygonsFilter polygonsFilter;

    Snapping snapping;

    RenderMode renderMode;

    Integer bufferDistance;

    /**
     * When true (API default), the returned shape will not cover large nearby water bodies.
     * Set to false to allow the shape to cover water bodies like large lakes, wide rivers, and seas.
     */
    Boolean removeWaterBodies;
}
