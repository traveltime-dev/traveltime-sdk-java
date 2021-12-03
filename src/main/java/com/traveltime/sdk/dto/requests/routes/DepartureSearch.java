package com.traveltime.sdk.dto.requests.routes;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.traveltime.sdk.dto.common.FullRange;
import com.traveltime.sdk.dto.common.Property;
import com.traveltime.sdk.dto.common.transportation.Transportation;
import jakarta.validation.Valid;
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
    List<String> arrivalLocationIds;
    @NonNull
    Transportation transportation;
    @NonNull
    Instant departureTime;
    @NonNull
    List<Property> properties;
    @Valid
    FullRange range;
}
