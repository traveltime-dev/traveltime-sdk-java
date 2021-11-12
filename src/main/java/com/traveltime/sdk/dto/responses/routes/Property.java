package com.traveltime.sdk.dto.responses.routes;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.traveltime.sdk.dto.common.Fares;
import com.traveltime.sdk.dto.common.route.Route;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.jackson.Jacksonized;

@Getter
@Jacksonized
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Property {
    @NonNull
    @Positive(message = "travelTime must be greater than 0")
    int travelTime;
    @NonNull
    @Positive(message = "distance must be greater than 0")
    int distance;
    @Valid
    Route route;
    @Valid
    Fares fares;
}
