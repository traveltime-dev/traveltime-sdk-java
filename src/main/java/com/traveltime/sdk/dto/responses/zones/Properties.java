package com.traveltime.sdk.dto.responses.zones;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Properties {
    TravelTime travelTimeReachable;
    TravelTime travelTimeAll;
    Double coverage;
}
