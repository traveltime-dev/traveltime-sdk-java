package com.traveltime.sdk.dto.requests.zones;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Property {
    @JsonProperty("travel_time_reachable")
    TRAVEL_TIME_REACHABLE,
    @JsonProperty("travel_time_all")
    TRAVEL_TIME_ALL,
    @JsonProperty("coverage")
    COVERAGE
}