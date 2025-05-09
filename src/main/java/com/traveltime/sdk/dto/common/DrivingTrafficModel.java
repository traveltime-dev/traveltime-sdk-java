package com.traveltime.sdk.dto.common;

import com.fasterxml.jackson.annotation.JsonProperty;

// TODO: Add documentation explaining different models
public enum DrivingTrafficModel {
    @JsonProperty("optimistic")
    OPTIMISTIC(),
    @JsonProperty("balanced")
    BALANCED(),
    @JsonProperty("pessimistic")
    PESSIMISTIC()
}
