package com.traveltime.sdk.dto.common.transportationfast;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

/**
 * Represents a multi-modal journey combining driving and public transportation.
 * Typically used for "park and ride" scenarios where users drive to a station,
 * then continue their journey via public transit.
 */
@Value
@Builder
@Jacksonized
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DrivingAndPublicTransport implements Transportation {
    /**
     * Time in seconds required to park a car at a transit station.
     * This accounts for the additional time needed to:
     * <li>Find a parking spot</li>
     * <li>Park the vehicle</li>
     * <li>Walk from the parking area to the station</li>
     *
     * If null, server determines the default value.
     */
    @Positive(message = "parkingTime must be greater than 0")
    Integer parkingTime;
}
