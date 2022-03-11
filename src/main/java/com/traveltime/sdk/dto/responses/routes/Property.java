package com.traveltime.sdk.dto.responses.routes;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.traveltime.sdk.dto.common.Fares;
import com.traveltime.sdk.dto.common.route.Route;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Property {
    @NonNull
    Integer travelTime;
    @NonNull
    Integer distance;
    Route route;
    Fares fares;
}
