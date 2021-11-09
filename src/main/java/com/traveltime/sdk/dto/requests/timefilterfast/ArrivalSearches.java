package com.traveltime.sdk.dto.requests.timefilterfast;

import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Getter
@Jacksonized
@Builder
@AllArgsConstructor
public class ArrivalSearches {
    @NonNull
    Iterable<ManyToOne> manyToOne;
    @NonNull
    Iterable<OneToMany> oneToMany;
}
