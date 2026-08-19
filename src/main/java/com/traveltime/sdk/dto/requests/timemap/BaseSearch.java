package com.traveltime.sdk.dto.requests.timemap;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.traveltime.sdk.dto.common.*;
import com.traveltime.sdk.dto.common.levelofdetail.LevelOfDetail;
import com.traveltime.sdk.dto.common.transportation.Transportation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.*;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class BaseSearch {
    @NonNull
    String id;

    @NonNull
    Coordinates coords;

    @Valid
    @NonNull
    Transportation transportation;

    @NonNull
    @Positive(message = "travelTime should be positive")
    Integer travelTime;

    Range range;

    LevelOfDetail levelOfDetail;

    // TODO: Replace to https://docs.traveltime.com/api/reference/isochrones#departure_searches-polygons_filter-limit
    Boolean singleShape;

    /**
     * Enable to remove holes from returned polygons.
     * Note that this will likely result in loss in accuracy.
     */
    Boolean noHoles;

    Snapping snapping;

    RenderMode renderMode;

    Integer bufferDistance;

    /**
     * When true (API default), the returned shape will not cover large nearby water bodies.
     * Set to false to allow the shape to cover water bodies like large lakes, wide rivers, and seas.
     */
    Boolean removeWaterBodies;
}
