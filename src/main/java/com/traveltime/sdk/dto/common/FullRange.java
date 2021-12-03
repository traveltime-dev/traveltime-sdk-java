package com.traveltime.sdk.dto.common;

import jakarta.validation.constraints.Positive;
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
    @Positive(message = "maxResults must be greater than 0")
    Integer maxResults;
    @NonNull
    @Positive(message = "width must be greater than 0")
    Integer width;
}