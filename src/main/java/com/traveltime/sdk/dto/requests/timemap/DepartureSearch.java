package com.traveltime.sdk.dto.requests.timemap;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.traveltime.sdk.dto.common.Coordinates;
import com.traveltime.sdk.dto.common.Snapping;
import com.traveltime.sdk.dto.common.levelofdetail.LevelOfDetail;
import com.traveltime.sdk.dto.common.transportation.Transportation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.time.Instant;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DepartureSearch {
    @NonNull
    String id;
    @NonNull
    Coordinates coords;
    @Valid
    @NonNull
    Transportation transportation;
    @NonNull
    Instant departureTime;
    @NonNull
    @Positive(message = "travelTime should be positive")
    Integer travelTime;
    @Valid
    Range range;
    LevelOfDetail levelOfDetail;
    Boolean singleShape;
    Boolean noHoles;
    Snapping snapPenalty;
}
