package com.traveltime.sdk.dto.requests.timefilter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.traveltime.sdk.dto.common.FullRange;
import com.traveltime.sdk.dto.common.Property;
import com.traveltime.sdk.dto.common.transportation.Transportation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.time.Instant;
import java.util.List;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DepartureSearch {
    @NonNull
    String id;
    @NonNull
    String departureLocationId;
    @NonNull
    @Singular
    List<String> arrivalLocationIds;
    @NonNull
    Transportation transportation;
    @NonNull
    Instant departureTime;
    @NonNull
    @Positive(message = "travelTime must be greater than 0")
    Integer travelTime;
    @NonNull
    @Singular
    List<Property> properties;
    @Valid
    FullRange range;
}
