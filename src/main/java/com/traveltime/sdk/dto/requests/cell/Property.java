package com.traveltime.sdk.dto.requests.cell;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Travel time statistics to return for each cell.
 */
public enum Property {
    @JsonProperty("min")
    MIN,

    @JsonProperty("max")
    MAX,

    @JsonProperty("mean")
    MEAN
}
