package com.traveltime.sdk.dto.common;

import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * Specifies how the routing algorithm should consider traffic conditions for driving modes.
 */
public enum DrivingTrafficModel {
    /**
     * Assumes lighter traffic conditions, resulting in shorter estimated travel times
     */
    @JsonProperty("optimistic")
    OPTIMISTIC,

    /**
     * Uses average traffic conditions, providing moderate time estimates
     */
    @JsonProperty("balanced")
    BALANCED,

    /**
     * Assumes heavier traffic conditions, resulting in longer estimated travel times
     */
    @JsonProperty("pessimistic")
    PESSIMISTIC
}
