package com.traveltime.sdk.dto.requests.proto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Country {
    NETHERLANDS("nl"),
    AUSTRIA("at"),
    BELGIUM("be"),
    GERMANY("de"),
    FRANCE("fr"),
    IRELAND("ie"),
    LITHUANIA("lt"),
    UNITED_KINGDOM("uk");

    private final String value;
}
