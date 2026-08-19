package com.traveltime.sdk.dto.requests.h3;

import jakarta.validation.Valid;
import java.util.List;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
public class ArrivalSearches {
    @Valid
    @NonNull
    List<FastSearch> manyToOne;

    @Valid
    @NonNull
    List<FastSearch> oneToMany;
}
