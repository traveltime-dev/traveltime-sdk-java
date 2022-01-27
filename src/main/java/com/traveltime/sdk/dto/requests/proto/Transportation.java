package com.traveltime.sdk.dto.requests.proto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Transportation {
    PUBLIC_TRANSPORT("pt", 0),
    WALKING_FERRY("walking+ferry", 7),
    CYCLING_FERRY("cycling+ferry", 6),
    DRIVING_FERRY("driving+ferry", 3);

    private final String value;
    private final Integer code;

    public static Transportation ofInt(int i) {
        for (Transportation t : Transportation.values()) {
            if (t.code.intValue() == i) return t;
        }

        throw new IllegalArgumentException("Transportation not found for value: " + i);
    }
}
