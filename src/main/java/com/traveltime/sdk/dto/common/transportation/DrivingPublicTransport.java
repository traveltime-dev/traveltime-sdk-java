package com.traveltime.sdk.dto.common.transportation;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.traveltime.sdk.dto.common.DrivingTrafficModel;
import com.traveltime.sdk.dto.common.MaxChanges;
import jakarta.validation.Valid;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

/**
 * Represents a multi-modal journey combining driving and any means of public transportation.
 * Typically used for "park and ride" scenarios where users drive to a station or stop,
 * then continue their journey via public transit.
 */
@Value
@Builder
@Jacksonized
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DrivingPublicTransport implements Transportation {
    /**
     * Time in seconds needed to board a public transportation vehicle.
     */
    @PositiveOrZero(message = "ptChangeDelay must not be negative")
    Integer ptChangeDelay;

    /**
     * Maximum driving time (in seconds) from origin to the station or stop.
     * <p>
     * If null, the server side decides the default value.
     */
    @PositiveOrZero(message = "drivingTimeToStation must not be negative")
    Integer drivingTimeToStation;

    /**
     * Time in seconds required to park a car.
     */
    @PositiveOrZero(message = "parkingTime must not be negative")
    Integer parkingTime;

    /**
     * Maximum time (in seconds) allowed for walking at:
     * - The origin to the first stop/station
     * - The final stop/station to the destination
     *
     * Notes:
     * - These limits apply independently, not cumulatively
     * - Only affects first and last walking segments
     * - Walking between transit legs is separately limited to 600s (10 min) each
     * - If null, server determines the default value
     */
    @PositiveOrZero(message = "walkingTime must not be negative")
    Integer walkingTime;

    /**
     * Time in seconds spent boarding a ferry. Covers only the boarding edges of ferry legs;
     * if null, the server determines the default value.
     */
    @PositiveOrZero(message = "boardingTime must not be negative")
    Integer boardingTime;

    /**
     * Configuration for limiting the number of transfers in a public transport journey.
     * By default, no restrictions apply to the number of transfers in the journey.
     */
    @Valid
    MaxChanges maxChanges;

    /**
     * Determines the traffic model to be used for driving-based routing calculations.
     * Specifies the assumptions about traffic conditions during the journey.
     * <p>
     * Available options:
     * <li>OPTIMISTIC: Assumes lighter traffic, resulting in shorter travel time estimates. </li>
     * <li>BALANCED: Assumes average traffic conditions for moderate time estimates. </li>
     * <li>PESSIMISTIC: Assumes heavier traffic, resulting in longer travel time estimates. </li>
     *
     * If null, a BALANCED model will be picked by default.
     */
    DrivingTrafficModel trafficModel;
}
