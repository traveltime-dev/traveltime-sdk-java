package com.traveltime.sdk.dto.requests.timemapfast;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
public class ArrivalSearches {
  @NonNull List<ManyToOne> manyToOne;
  @NonNull List<OneToMany> oneToMany;
}
