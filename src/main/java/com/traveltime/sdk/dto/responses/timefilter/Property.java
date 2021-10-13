package com.traveltime.sdk.dto.responses.timefilter;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class Property {
    Integer travelTime;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    Integer distance;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    Iterable<DistanceBreakdown> distanceBreakdown;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    Fares fares;
}
