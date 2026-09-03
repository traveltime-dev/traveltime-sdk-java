package com.traveltime.sdk.dto.common.transportation;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.traveltime.sdk.dto.common.MaxChanges;
import jakarta.validation.Valid;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

/**
 * Represents a multi-modal journey combining cycling and public transportation, where users
 * cycle to a station or stop and continue their journey via public transit.
 */
@Value
@Builder
@Jacksonized
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CyclingPublicTransport implements Transportation {
    /**
     * Time in seconds needed to board a public transportation vehicle.
     */
    @PositiveOrZero(message = "ptChangeDelay must not be negative")
    Integer ptChangeDelay;

    /**
     * Maximum cycling time (in seconds) from origin to the station or stop.
     * <p>
     * If null, the server side decides the default value.
     */
    @PositiveOrZero(message = "cyclingTimeToStation must not be negative")
    Integer cyclingTimeToStation;

    /**
     * Time in seconds required to park a bicycle.
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
}
