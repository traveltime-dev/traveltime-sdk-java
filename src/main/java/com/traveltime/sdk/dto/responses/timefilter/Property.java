package com.traveltime.sdk.dto.responses.timefilter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.traveltime.sdk.dto.common.Fares;
import com.traveltime.sdk.dto.common.route.Route;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Getter
@Jacksonized
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
public class Property {
    Integer travelTime;
    Integer distance;
    List<DistanceBreakdown> distanceBreakdown;
    Fares fares;
    Route route;
}
