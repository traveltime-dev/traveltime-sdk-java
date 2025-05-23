package com.traveltime.sdk.dto.common.route;

import java.time.OffsetDateTime;
import java.util.List;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
public class Route {
    /**
     * Time of departure from location
     */
    @NonNull
    OffsetDateTime departureTime;

    /**
     * Time of arrival to location
     */
    @NonNull
    OffsetDateTime arrivalTime;

    /**
     * Sequential route segments, each containing different details about the segment, for example:
     * - Transport mode (walk, bike, car, etc.)
     * - Geographical coordinates
     * - Direction instructions (e.g., "Drive 44m on Elizabeth Road")
     * - ...
     * Together representing the complete journey from origin to destination.
     */
    @NonNull
    List<Part> parts;
}
