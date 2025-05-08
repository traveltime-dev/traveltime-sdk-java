package com.traveltime.sdk.dto.common;

import jakarta.validation.Valid;
import java.util.List;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
public class FareBreakdown {
  @NonNull List<String> modes;
  @NonNull List<Integer> routePartIds;
  @Valid @NonNull List<Ticket> tickets;
}
