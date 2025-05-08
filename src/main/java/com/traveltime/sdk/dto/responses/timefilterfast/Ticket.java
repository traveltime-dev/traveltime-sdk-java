package com.traveltime.sdk.dto.responses.timefilterfast;

import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
public class Ticket {
  @NonNull String type;
  @NonNull Double price;
  @NonNull String currency;
}
