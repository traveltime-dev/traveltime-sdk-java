package com.traveltime.sdk.dto.responses.zones;

import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
public class TravelTime {
    @NonNull
    Integer min;

    @NonNull
    Integer max;

    @NonNull
    Integer mean;

    @NonNull
    Integer median;
}
