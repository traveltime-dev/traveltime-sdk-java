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

    @Positive(message = "drivingTimeToStation must be greater than 0")
    Integer drivingTimeToStation;

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

    // TODO: Fix lack of documentation
    DrivingTrafficModel trafficModel;
}
