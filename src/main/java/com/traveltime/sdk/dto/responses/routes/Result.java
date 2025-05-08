package com.traveltime.sdk.dto.responses.routes;

import jakarta.validation.Valid;
import java.util.List;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
public class Result {
  @NonNull String searchId;
  @Valid @NonNull List<Location> locations;
  @NonNull List<String> unreachable;
}
