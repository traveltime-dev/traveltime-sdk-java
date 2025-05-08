package com.traveltime.sdk.dto.common;

import jakarta.validation.constraints.Positive;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
public class MaxChanges {
    @NonNull
    Boolean enabled;

    @NonNull
    @Positive(message = "limit must be greater than 0")
    Integer limit;
}
