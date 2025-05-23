package com.traveltime.sdk.dto.requests.zones;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Property {
    /**
     *  Provides a statistical summary of travel times for only reachable postcodes in the sector.
     */
    @JsonProperty("travel_time_reachable")
    TRAVEL_TIME_REACHABLE,
    /**
     * Provides a statistical summary of travel times for all postcodes in the sector.
     */
    @JsonProperty("travel_time_all")
    TRAVEL_TIME_ALL,
    /**
     * Percentage of reachable postcodes in the sector.
     */
    @JsonProperty("coverage")
    COVERAGE
}
