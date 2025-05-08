package com.traveltime.sdk.dto.requests.timefilterfast;

import java.util.List;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
public class ArrivalSearches {
  @NonNull List<ManyToOne> manyToOne;
  @NonNull List<OneToMany> oneToMany;
}
