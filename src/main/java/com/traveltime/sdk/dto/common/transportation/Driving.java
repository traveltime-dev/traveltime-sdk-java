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
     * If null, crossing of country borders is allowed.
     */
    Boolean disableBorderCrossing;

    // TODO: Fix lack of documentation
    DrivingTrafficModel trafficModel;
}
