package com.traveltime.sdk.dto.requests.timemapfast;

import jakarta.validation.Valid;
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
    @Valid
    @NonNull
    List<ManyToOne> manyToOne;

    @Valid
    @NonNull
    List<OneToMany> oneToMany;
}
