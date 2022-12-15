package com.traveltime.sdk.dto.requests.proto;

import lombok.AllArgsConstructor;
import lombok.Getter;

public interface Transportation {
    String getValue();
    Integer getCode();

    Transportation PUBLIC_TRANSPORT = Modes.PUBLIC_TRANSPORT;
    Transportation WALKING_FERRY = Modes.WALKING_FERRY;
    Transportation CYCLING_FERRY = Modes.CYCLING_FERRY;
    Transportation DRIVING_FERRY = Modes.DRIVING_FERRY;
    Transportation WALKING = Modes.WALKING;
    Transportation CYCLING = Modes.CYCLING;
    Transportation DRIVING = Modes.DRIVING;

    @Getter
    @AllArgsConstructor
    enum Modes implements Transportation {
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

    @Getter
    @AllArgsConstructor
    class Custom implements Transportation {
        private final String value;
        private final Integer code;
    }

    static Transportation[] values() {
        return Modes.values();
    }
}
