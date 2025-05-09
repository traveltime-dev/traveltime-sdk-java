package com.traveltime.sdk.dto.common.transportation;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.traveltime.sdk.dto.common.MaxChanges;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PublicTransport implements Transportation {
    /**
     * Time in seconds needed to board a public transportation vehicle.
     */
    @Positive(message = "ptChangeDelay must be greater than 0")
    Integer ptChangeDelay;

    /**
     * Maximum time (in seconds) allowed for walking at:
     * <li>The origin to the first stop/station</li>
     * <li>The final stop/station to the destination</li>
     *
     * Notes:
     * <li>These limits apply independently, not cumulatively</li>
     * <li>Only affects the first and last walking segments</li>
     * <li>Walking between transit legs is separately limited to 600s (10 min) each</li>
     * <li>If null, server determines the default value</li>
     */
    @Positive(message = "walkingTime must be greater than 0")
    Integer walkingTime;

    /**
     * Configuration for limiting the number of transfers in a public transport journey.
     * By default, no restrictions apply to the number of transfers in the journey.
     */
    @Valid
    MaxChanges maxChanges;
}
