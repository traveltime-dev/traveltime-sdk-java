package com.traveltime.sdk.dto.responses.timefilter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.traveltime.sdk.dto.common.Fares;
import com.traveltime.sdk.dto.common.route.Route;
import java.util.List;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Property {
  Integer travelTime;
  Integer distance;
  List<DistanceBreakdown> distanceBreakdown;
  Fares fares;
  Route route;
}
