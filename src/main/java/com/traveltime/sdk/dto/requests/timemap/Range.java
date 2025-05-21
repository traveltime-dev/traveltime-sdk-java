package com.traveltime.sdk.dto.requests.timemap;

import jakarta.validation.constraints.Positive;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

/**
 * Configures time range parameters.
 * <p>
 * When enabled, this allows searching for routes departing within a time window
 * rather than at a single specific time, returning a combined result that represents
 * all possible journeys within that window.
 * <p>
 * Time range functionality is primarily applicable to scheduled transportation modes:
 * public_transport, coach, bus, train, and driving+train combinations.
 * For other transportation modes (like walking, cycling, or driving), these range
 * parameters will be ignored.
 */
@Value
@Builder
@Jacksonized
@AllArgsConstructor
public class Range {
    /**
     * Controls whether the time range functionality is active.
     * <p>
     * When true, the routing algorithm will consider departures within a window
     * starting at the specified departure time and extending forward by the width value.
     * When false, only the exact departure time will be considered, and the width parameter will be ignored.
     */
    @NonNull
    Boolean enabled;

    /**
     * Specifies the duration of the departure time window in seconds.
     * <p>
     * The window always starts at the specified departure time and extends forward
     * by this amount. For example, with a departure time of 09:00 and a width of
     * 3600 (1 hour), all journeys departing between 09:00 and 10:00 will be included
     * in the results.
     * <p>
     * Must be a positive value. Maximum allowed value is 43200 (12 hours).
     */
    @NonNull
    @Positive(message = "width must be greater than 0")
    Integer width;
}
