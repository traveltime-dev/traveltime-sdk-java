package com.traveltime.sdk.dto.requests.timemap;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.traveltime.sdk.dto.common.Coordinates;
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
    Boolean singleShape;
    Boolean noHoles;
}
