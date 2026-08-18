package com.traveltime.sdk.dto.requests.geohash;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Travel time statistics to return for each geohash cell.
 */
public enum Property {
    @JsonProperty("min")
    MIN,

    @JsonProperty("max")
    MAX,

    @JsonProperty("mean")
    MEAN
}
