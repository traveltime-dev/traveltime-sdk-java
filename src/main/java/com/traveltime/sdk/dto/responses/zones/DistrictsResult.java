package com.traveltime.sdk.dto.responses.zones;

import java.util.List;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
public class DistrictsResult {
  @NonNull String searchId;
  @NonNull List<Zone> districts;
}
