package com.traveltime.sdk.dto.common.route;

import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.time.OffsetDateTime;
import java.util.List;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
public class Route {
    @NonNull
    OffsetDateTime departureTime;
    @NonNull
    OffsetDateTime arrivalTime;
    @NonNull
    List<Part> parts;
}
