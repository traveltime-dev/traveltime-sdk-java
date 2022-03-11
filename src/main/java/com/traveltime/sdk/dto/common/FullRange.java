package com.traveltime.sdk.dto.common;

import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
public class FullRange {
    @NonNull
    Boolean enabled;
    @NonNull
    Integer maxResults;
    @NonNull
    Integer width;
}