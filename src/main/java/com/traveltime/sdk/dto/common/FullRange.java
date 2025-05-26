package com.traveltime.sdk.dto.common;

import jakarta.validation.constraints.Positive;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

/**
 * Configures time range search parameters for journey planning.
 * <p>
 * When enabled, this feature allows searches to consider journeys within a specified
 * time window rather than at an exact time only, providing multiple journey options.
 */
@Value
@Builder
@Jacksonized
@AllArgsConstructor
public class FullRange {
    /**
     * Controls whether the time range search is active.
     * <p>
     * When enabled, adds a time window to the specified departure or arrival time.
     * Journey results will include any options that depart/arrive within this window.
     * <p>
     * Note:
     * <li> Disabled by default
     * <li> Only supported for public transportation modes (public_transport, coach, bus,
     *   train, driving+train, driving+public_transport, cycling+public_transport)
     * <li> Ignored for other transportation modes
     */
    @NonNull
    Boolean enabled;

    /**
     * Maximum number of results to return. Limited to 5 results.
     */
    @NonNull
    @Positive(message = "maxResults must be greater than 0")
    Integer maxResults;

    /**
     * Defines the width of the time range window in seconds.
     * <p>
     * Behavior varies based on whether searching by departure or arrival time:
     * <li> For departure time: Window extends forward (e.g., 9:00am with 1-hour width
     *   includes journeys departing 9:00am-10:00am)
     * <li> For arrival time: Window extends backward (e.g., 9:00am with 1-hour width
     *   includes journeys arriving 8:00am-9:00am)
     * <p>
     * Maximum allowed value: 43,200 seconds (12 hours)
     */
    @NonNull
    @Positive(message = "width must be greater than 0")
    Integer width;
}
