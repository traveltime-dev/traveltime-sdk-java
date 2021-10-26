package com.traveltime.sdk.dto.requests.timemap;

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
    @Positive(message = "width must be greater than 0")
    Integer width;
}
