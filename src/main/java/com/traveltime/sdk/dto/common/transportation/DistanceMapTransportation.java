package com.traveltime.sdk.dto.common.transportation;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * The transportation modes accepted by the distance-map endpoint. Public transport based
 * modes are not supported, as distances are undefined for public transport journeys.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = Walking.class, name = "walking"),
    @JsonSubTypes.Type(value = Cycling.class, name = "cycling"),
    @JsonSubTypes.Type(value = Driving.class, name = "driving"),
    @JsonSubTypes.Type(value = Ferry.class, name = "ferry"),
    @JsonSubTypes.Type(value = CyclingFerry.class, name = "cycling+ferry"),
    @JsonSubTypes.Type(value = DrivingFerry.class, name = "driving+ferry"),
})
public interface DistanceMapTransportation {}
