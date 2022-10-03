package com.traveltime.sdk.dto.requests.protodistance;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Transportation {
    DRIVING_FERRY("driving+ferry", 3),
    WALKING("walking", 4),
    DRIVING("driving", 1);

    private final String value;
    private final Integer code;
}
