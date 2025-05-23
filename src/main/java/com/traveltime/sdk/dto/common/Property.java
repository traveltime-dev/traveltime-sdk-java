package com.traveltime.sdk.dto.common;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Properties to include in the response.
 * Not all properties are available for all transportation modes.
 */
public enum Property {
    /**
     * Total estimated travel time for the route in seconds.
     * Available for all transportation modes.
     */
    @JsonProperty("travel_time")
    TRAVEL_TIME,

    /**
     * Total distance of the route in meters.
     * Driving, walking and cycling modes only.
     */
    @JsonProperty("distance")
    DISTANCE,

    /**
     * Detailed path information.
     */
    @JsonProperty("route")
    ROUTE,

    /**
     * Cost information for the journey.
     */
    @JsonProperty("fares")
    FARES
}
