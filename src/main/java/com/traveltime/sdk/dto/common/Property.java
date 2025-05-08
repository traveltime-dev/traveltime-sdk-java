package com.traveltime.sdk.dto.common;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Property {
  @JsonProperty("travel_time")
  TRAVEL_TIME,
  @JsonProperty("distance")
  DISTANCE,
  @JsonProperty("route")
  ROUTE,
  @JsonProperty("fares")
  FARES
}
