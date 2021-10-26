package com.traveltime.sdk;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AcceptType {
    ApplicationJson("application/json"),
    ApplicationWktJson("application/vnd.wkt+json");

    private final String value;
}
