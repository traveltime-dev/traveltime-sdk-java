package com.traveltime.sdk.dto.common.transportation;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.traveltime.sdk.dto.common.DrivingTrafficModel;
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
public class DrivingFerry implements Transportation {
    /**
     * Time in seconds required to board a ferry.
     * If null, `boardingTime` is 0.
     */
    @Positive(message = "boardingTime must be greater than 0")
    Integer boardingTime;

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
