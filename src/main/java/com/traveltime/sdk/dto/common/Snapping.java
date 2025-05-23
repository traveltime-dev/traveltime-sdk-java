package com.traveltime.sdk.dto.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

/**
 * Represents configuration for snapping coordinates to a road network during journey planning.
 * Controls how journey start/end points are processed and whether off-road distances affect overall calculations.
 */
@Value
@Builder
@Jacksonized
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Snapping {

    /**
     * Defines which road types are valid for journey start/end points based on their accessibility characteristics.
     * This affects where a journey can begin or terminate within the routing network.
     */
    public enum AcceptRoads {
        /**
         * Journey can only start or end on roads that are accessible by both cars and pedestrians.
         * This effectively means journeys cannot start/end on motorways, highways, or other vehicle-only roadways.
         * Ensures journey endpoints are accessible to pedestrians, which is important for first/last mile connectivity.
         */
        @JsonProperty("both_drivable_and_walkable")
        BOTH_DRIVABLE_AND_WALKABLE,

        /**
         * Journey can start or end on any road accessible by a car (including motorways).
         * This option maximizes vehicle routing options by allowing connections to all drivable roads,
         * but may result in journey endpoints that are not accessible to pedestrians.
         */
        @JsonProperty("any_drivable")
        ANY_DRIVABLE
    }

    /**
     * Determines how off-road distances are factored into journey calculations.
     * Controls whether the time/distance required to reach the actual road network is included in metrics.
     */
    public enum SnapPenalty {
        /**
         * Walking time and distance from the departure location to the nearest road
         * and from the nearest road to the arrival location are added to the total travel time and distance of a journey.
         * This provides more realistic door-to-door journey metrics that include the "first and last mile" segments.
         */
        @JsonProperty("enabled")
        ENABLED,
        /**
         * Walking times and distances are not added to the total reported values
         * (i.e., the journey effectively starts and ends at the nearest points on the road network).
         * This approach focuses exclusively on the road network portion of a journey,
         * which may be preferred for vehicle-only routing or when endpoints are already on roads.
         */
        @JsonProperty("disabled")
        DISABLED
    }

    SnapPenalty penalty;
    AcceptRoads acceptRoads;
}
