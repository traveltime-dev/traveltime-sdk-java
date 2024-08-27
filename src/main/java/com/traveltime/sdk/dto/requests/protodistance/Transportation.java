package com.traveltime.sdk.dto.requests.protodistance;

import lombok.AllArgsConstructor;
import lombok.Getter;

public interface Transportation {
    String getValue();
    Integer getCode();

    Transportation WALKING_FERRY = Transportation.Modes.WALKING_FERRY;
    Transportation DRIVING_FERRY = Transportation.Modes.DRIVING_FERRY;
    Transportation CYCLING_FERRY = Transportation.Modes.CYCLING_FERRY;
    Transportation WALKING = Transportation.Modes.WALKING;
    Transportation DRIVING = Transportation.Modes.DRIVING;
    Transportation CYCLING = Transportation.Modes.CYCLING;

    @Getter
    @AllArgsConstructor
    enum Modes implements Transportation {
        WALKING_FERRY("walking+ferry", 7),
        DRIVING_FERRY("driving+ferry", 3),
        CYCLING_FERRY("cycling+ferry", 6),
        CYCLING("cycling", 5),
        WALKING("walking", 4),
        DRIVING("driving", 1);

        private final String value;
        private final Integer code;
    }

    @Getter
    @AllArgsConstructor
    class Custom implements Transportation {
        private final String value;
        private final Integer code;
    }

    static Transportation[] values() {
        return Transportation.Modes.values();
    }
}
