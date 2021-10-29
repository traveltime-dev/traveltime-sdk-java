package com.traveltime.sdk;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AcceptType {
    APPLICATION_JSON("application/json"),
    APPLICATION_WKT_JSON("application/vnd.wkt+json");

    private final String value;
}
