package com.traveltime.sdk.dto.requests.timemapfast.levelofdetail;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Level {
    @JsonProperty("lowest")
    LOWEST,
    @JsonProperty("low")
    LOW,
    @JsonProperty("medium")
    MEDIUM,
    @JsonProperty("high")
    HIGH,
    @JsonProperty("highest")
    HIGHEST
}
