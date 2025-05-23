package com.traveltime.sdk.dto.common.transportation;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

/**
 * Represents a multi-modal journey combining cycling and ferry transportation.
 * Used for routes where cyclists board ferries to cross bodies of water while
 * bringing their bicycles onboard.
 */
@Value
@Builder
@Jacksonized
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CyclingFerry implements Transportation {
    /**
     * Time in seconds required to board a ferry with a bicycle.
     * <p>
     * This could account for extra time needed for:
     * <li>Queuing with the bicycle</li>
     * <li>Purchasing tickets</li>
     *
     * If null, defaults to 0 seconds (no additional boarding time).
     */
    @Positive(message = "boardingTime must be greater than 0")
    Integer boardingTime;
}
