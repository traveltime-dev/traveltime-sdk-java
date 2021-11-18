package com.traveltime.sdk.dto.requests.proto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Transportation {
    WALKING_FERRY("walking+ferry", 7),
    CYCLING_FERRY("cycling+ferry", 6),
    DRIVING_FERRY("driving+ferry", 3);

    private final String value;
    private final Integer code;
}