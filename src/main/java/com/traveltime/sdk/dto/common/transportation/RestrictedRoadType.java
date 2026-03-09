package com.traveltime.sdk.dto.common.transportation;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum RestrictedRoadType {
    /**
     * Unpaved roads that only allow very slow driving speed or even require an off-road capable vehicle.
     */
    @JsonProperty("restricted")
    RESTRICTED,
    /**
     * Roads that are not publicly accessible and may require a special permit. By default,
     * all of these roads are excluded from the search.
     */
    @JsonProperty("track")
    TRACK
}
