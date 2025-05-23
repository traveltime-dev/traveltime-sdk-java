package com.traveltime.sdk.dto.common.transportation;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.traveltime.sdk.dto.common.DrivingTrafficModel;
import com.traveltime.sdk.dto.common.MaxChanges;
import jakarta.validation.Valid;
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
public class DrivingTrain implements Transportation {
    /**
     * Time in seconds needed to board a public transportation vehicle.
     */
    @Positive(message = "ptChangeDelay must be greater than 0")
    Integer ptChangeDelay;

    /**
     * Maximum driving time (in seconds) from origin to train station.
     * <p>
     * This limit applies to the "park and ride" scenario where the journey
     * begins with driving to a train station before continuing via rail.
     * <p>
     * If null, the server side decides the default value.
     */
    @Positive(message = "drivingTimeToStation must be greater than 0")
    Integer drivingTimeToStation;

    /**
     * Time in seconds required to park a car.
     */
    @Positive(message = "parkingTime must be greater than 0")
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
    @Positive(message = "walkingTime must be greater than 0")
    Integer walkingTime;

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
