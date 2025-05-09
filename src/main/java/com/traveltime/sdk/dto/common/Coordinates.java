package com.traveltime.sdk.dto.common;

import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
public class Coordinates {
    /**
     * Represents the latitude coordinate of a geographical location.
     * Expected to be a non-null value in the range of valid latitude coordinates (-90.0 to 90.0).
     */
    @NonNull
    Double lat;

    /**
     * Represents the longitude coordinate of a geographical location.
     * Expected to be a non-null value in the range of valid longitude coordinates (-180.0 to 180.0).
     */
    @NonNull
    Double lng;
}
