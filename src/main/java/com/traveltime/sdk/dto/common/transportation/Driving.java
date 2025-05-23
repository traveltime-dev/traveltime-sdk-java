package com.traveltime.sdk.dto.common.transportation;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.traveltime.sdk.dto.common.DrivingTrafficModel;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Driving implements Transportation {
    /**
     * If set to true, the crossing of country borders is disabled.
     * `null` defaults to `false`.
     */
    Boolean disableBorderCrossing;

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
