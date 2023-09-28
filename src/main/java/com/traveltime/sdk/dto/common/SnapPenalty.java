package com.traveltime.sdk.dto.common;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum SnapPenalty {
    @JsonProperty("enabled")
    ENABLED,
    @JsonProperty("disabled")
    DISABLED
}
