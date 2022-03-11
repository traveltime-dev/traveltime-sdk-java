package com.traveltime.sdk.dto.requests.timemap;

import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
public class Range {
    @NonNull
    Boolean enabled;
    @NonNull
    Integer width;
}
