package com.traveltime.sdk.dto.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Snapping {

    public enum AcceptRoads {
        @JsonProperty("both_drivable_and_walkable")
        BOTH_DRIVABLE_AND_WALKABLE,
        @JsonProperty("any_drivable")
        ANY_DRIVABLE
    }

    public enum SnapPenalty {
        @JsonProperty("enabled")
        ENABLED,
        @JsonProperty("disabled")
        DISABLED
    }

    SnapPenalty penalty;
    AcceptRoads acceptRoads;
}
