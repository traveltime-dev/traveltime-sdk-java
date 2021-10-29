package com.traveltime.sdk.dto.requests.timefilter;

import jakarta.validation.constraints.Positive;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Builder
@Getter
@Jacksonized
@AllArgsConstructor
public class Range {
    @NonNull
    Boolean enabled;
    @NonNull
    @Positive(message = "maxResults must be greater than 0")
    Integer maxResults;
    @NonNull
    @Positive(message = "width must be greater than 0")
    Integer width;
}