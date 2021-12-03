package com.traveltime.sdk.dto.common.route;

import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDateTime;
import java.util.List;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
public class Route {
    @NonNull
    LocalDateTime departureTime;
    @NonNull
    LocalDateTime arrivalTime;
    @NonNull
    List<Part> parts;
}
