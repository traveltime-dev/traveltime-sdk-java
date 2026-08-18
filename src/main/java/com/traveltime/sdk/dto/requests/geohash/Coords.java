package com.traveltime.sdk.dto.requests.geohash;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Departure or arrival location of a geohash search, given either as a latitude/longitude pair
 * or as the index of a geohash cell whose centroid is used as the location.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
@JsonSubTypes({
    @JsonSubTypes.Type(value = LatLngCoords.class),
    @JsonSubTypes.Type(value = GeohashCentroidCoords.class),
})
public interface Coords {}
