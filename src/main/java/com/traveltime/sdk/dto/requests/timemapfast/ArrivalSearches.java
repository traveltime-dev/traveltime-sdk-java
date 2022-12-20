package com.traveltime.sdk.dto.requests.timemapfast;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
public class ArrivalSearches {
    @NonNull
    List<ManyToOne> manyToOne;
    @NonNull
    List<OneToMany> oneToMany;
}
