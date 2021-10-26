package com.traveltime.sdk.dto.responses.timefilter;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Getter
@Jacksonized
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Property {
    @NonNull
    Integer travelTime;
    Integer distance;
    Iterable<DistanceBreakdown> distanceBreakdown;
    Fares fares;
}
