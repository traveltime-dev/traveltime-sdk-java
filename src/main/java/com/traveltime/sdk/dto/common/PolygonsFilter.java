package com.traveltime.sdk.dto.common;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
public class PolygonsFilter {
    /**
     * At most this amount of largest polygons will be returned in a single shape.
     * Must be greater than 0.
     */
    @NonNull
    @Positive(message = "limit must be greater than 0")
    Integer limit;
}
