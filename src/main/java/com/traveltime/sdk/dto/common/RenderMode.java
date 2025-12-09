package com.traveltime.sdk.dto.common;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Rendering mode for polygon output optimization.
 */
public enum RenderMode {
    /**
     * The shape matches time-filter results as much as possible. This is the
     * default.
     */
    @JsonProperty("approximate_time_filter")
    APPROXIMATE_TIME_FILTER,

    /**
     * The shape looks as if traversed roads of the search have been painted over
     * with a wide brush.
     */
    @JsonProperty("road_buffering")
    ROAD_BUFFERING,
}
