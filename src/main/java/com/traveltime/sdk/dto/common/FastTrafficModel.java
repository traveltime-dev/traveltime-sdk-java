package com.traveltime.sdk.dto.common;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Specifies the level of traffic used for driving journeys on the fast endpoints.
 * Can only be used with the driving and driving+ferry transportation types.
 */
public enum FastTrafficModel {
    /**
     * Represents the typical traffic conditions for a midweek morning (the server default)
     */
    @JsonProperty("peak")
    PEAK,

    /**
     * Represents the typical traffic conditions at night time
     */
    @JsonProperty("off_peak")
    OFF_PEAK
}
