package com.traveltime.sdk.dto.requests.timemap;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.traveltime.sdk.dto.common.Coordinates;
import com.traveltime.sdk.dto.common.Snapping;
import com.traveltime.sdk.dto.common.levelofdetail.LevelOfDetail;
import com.traveltime.sdk.dto.common.transportation.Transportation;
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
    Coordinates coords;

    @Valid
    @NonNull
    Transportation transportation;

    @NonNull
    Instant arrivalTime;

    @NonNull
    @Positive(message = "travelTime should be positive")
    Integer travelTime;

    Range range;
    LevelOfDetail levelOfDetail;

    //TODO: Replace to https://docs.traveltime.com/api/reference/isochrones#departure_searches-polygons_filter-limit
    Boolean singleShape;

    /**
     * Enable to remove holes from returned polygons.
     * Note that this will likely result in loss in accuracy.
     */
    Boolean noHoles;
    Snapping snapping;
}
