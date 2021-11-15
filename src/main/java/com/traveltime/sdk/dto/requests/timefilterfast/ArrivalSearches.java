package com.traveltime.sdk.dto.requests.timefilterfast;

import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Getter
@Jacksonized
@Builder
@AllArgsConstructor
public class ArrivalSearches {
    @NonNull
    List<ManyToOne> manyToOne;
    @NonNull
    List<OneToMany> oneToMany;
}
