package com.traveltime.sdk.dto.responses.timemap;

import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
public class Rectangle {
  @NonNull Double minLat;
  @NonNull Double maxLat;
  @NonNull Double minLng;
  @NonNull Double maxLng;
}
