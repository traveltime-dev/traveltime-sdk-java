package com.traveltime.sdk.dto.responses.timefilterfast;

import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
public class Location {
    @NonNull
    String id;

    @NonNull
    Properties properties;
}
