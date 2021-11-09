package com.traveltime.sdk.dto.requests.timefilter;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Properties {
    @JsonProperty("travel_time")
    TRAVEL_TIME,
    @JsonProperty("distance")
    DISTANCE,
    @JsonProperty("route")
    ROUTE,
    @JsonProperty("fares")
    FARES
}
