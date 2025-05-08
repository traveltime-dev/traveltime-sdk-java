package com.traveltime.sdk.dto.requests.timemap;

import java.util.List;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
public class Intersection {
  @NonNull String id;
  @NonNull @Singular List<String> searchIds;
}
