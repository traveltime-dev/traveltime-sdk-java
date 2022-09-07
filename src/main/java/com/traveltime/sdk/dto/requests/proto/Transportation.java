package com.traveltime.sdk.dto.requests.proto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Transportation {
    PUBLIC_TRANSPORT("pt", 0),
    WALKING_FERRY("walking+ferry", 7),
    CYCLING_FERRY("cycling+ferry", 6),
    DRIVING_FERRY("driving+ferry", 3),
    WALKING("walking", 4),
    CYCLING("cycling", 5),
    DRIVING("driving", 1);

    private final String value;
    private final Integer code;
}