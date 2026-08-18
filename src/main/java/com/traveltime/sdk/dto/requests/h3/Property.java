package com.traveltime.sdk.dto.requests.h3;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Travel time statistics to return for each H3 cell.
 */
public enum Property {
    @JsonProperty("min")
    MIN,

    @JsonProperty("max")
    MAX,

    @JsonProperty("mean")
    MEAN
}
