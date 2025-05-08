package com.traveltime.sdk.dto.requests.proto;

public enum RequestType {
  // single departure location and multiple arrival locations
  ONE_TO_MANY,
  // single arrival location and multiple departure locations
  MANY_TO_ONE,
}
