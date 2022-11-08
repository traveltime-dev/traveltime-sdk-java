package com.traveltime.sdk.dto.requests.protodistance;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Transportation {
    DRIVING("driving", 1),
    DRIVING_FERRY("driving+ferry", 3),
    WALKING("walking", 4),
    WALKING_FERRY("walking+ferry", 7);

    private final String value;
    private final Integer code;
}
