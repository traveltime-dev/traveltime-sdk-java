package com.traveltime.sdk.dto.requests.h3;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Departure or arrival location of an H3 search, given either as a latitude/longitude pair
 * or as the index of an H3 cell whose centroid is used as the location.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
@JsonSubTypes({
    @JsonSubTypes.Type(value = LatLngCoords.class),
    @JsonSubTypes.Type(value = H3CentroidCoords.class),
})
public interface Coords {}
