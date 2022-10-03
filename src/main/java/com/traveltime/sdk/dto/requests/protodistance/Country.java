package com.traveltime.sdk.dto.requests.protodistance;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Country {
    IRELAND("ie"),
    UNITED_KINGDOM("uk");

    private final String value;
}
