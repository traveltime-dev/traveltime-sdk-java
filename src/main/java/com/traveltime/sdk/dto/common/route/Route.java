package com.traveltime.sdk.dto.common.route;

import java.time.OffsetDateTime;
import java.util.List;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
public class Route {
  @NonNull OffsetDateTime departureTime;
  @NonNull OffsetDateTime arrivalTime;
  @NonNull List<Part> parts;
}
