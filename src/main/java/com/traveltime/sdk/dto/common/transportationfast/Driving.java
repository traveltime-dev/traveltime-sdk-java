package com.traveltime.sdk.dto.common.transportationfast;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.traveltime.sdk.dto.common.FastTrafficModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
@NoArgsConstructor(force = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Driving implements Transportation {
    /**
     * The level of traffic used for the journey. If null, the server determines the
     * default value.
     */
    FastTrafficModel trafficModel;
}
