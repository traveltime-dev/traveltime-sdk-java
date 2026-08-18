package com.traveltime.sdk.dto.requests.h3;

import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
public class LatLngCoords implements Coords {
    /**
     * Latitude of the location, in the range -90.0 to 90.0.
     */
    @NonNull
    Double lat;

    /**
     * Longitude of the location, in the range -180.0 to 180.0.
     */
    @NonNull
    Double lng;
}
