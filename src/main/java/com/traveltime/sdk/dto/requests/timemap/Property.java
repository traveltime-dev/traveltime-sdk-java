package com.traveltime.sdk.dto.requests.timemap;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Properties to be returned about the shapes.
 */
public enum Property {
    /**
     * Returns true when the journey to every part of the shape only involves walking,
     * e.g. for a public transport search in an area with no public transport available.
     */
    @JsonProperty("is_only_walking")
    IS_ONLY_WALKING
}
