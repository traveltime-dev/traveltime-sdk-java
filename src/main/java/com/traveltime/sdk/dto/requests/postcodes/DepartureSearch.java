package com.traveltime.sdk.dto.requests.postcodes;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.traveltime.sdk.dto.common.Coordinates;
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

    @NonNull
    @Singular
    List<Property> properties;

    @Valid
    FullRange range;

    Snapping snapping;
}
