package com.traveltime.sdk.dto.requests.timefilter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.traveltime.sdk.dto.common.FullRange;
import com.traveltime.sdk.dto.common.Property;
import com.traveltime.sdk.dto.common.Snapping;
import com.traveltime.sdk.dto.common.transportation.Transportation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import java.time.Instant;
import java.util.List;
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
    @Singular
    List<String> departureLocationIds;

    @NonNull
    String arrivalLocationId;

    @NonNull
    Transportation transportation;

    @NonNull
    Instant arrivalTime;

    @NonNull
    @Positive(message = "travelTime must be greater than 0")
    Integer travelTime;

    @NonNull
    List<Property> properties;

    @Valid
    FullRange range;

    Snapping snapping;
}
