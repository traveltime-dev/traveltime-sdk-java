package com.traveltime.sdk.dto.responses.timemap;

import lombok.*;
import lombok.extern.jackson.Jacksonized;

/**
 * Represents a rectangular geographic bounding box defined by minimum and maximum latitude and longitude coordinates.
 */
@Value
@Builder
@Jacksonized
@AllArgsConstructor
public class Rectangle {

    /**
     * The minimum latitude (southern boundary) of the rectangle in decimal degrees.
     * Valid range: -90.0 to +90.0, where negative values represent the Southern Hemisphere.
     */
    @NonNull
    Double minLat;

    /**
     * The maximum latitude (northern boundary) of the rectangle in decimal degrees.
     * Valid range: -90.0 to +90.0, where positive values represent the Northern Hemisphere.
     */
    @NonNull
    Double maxLat;

    /**
     * The minimum longitude (western boundary) of the rectangle in decimal degrees.
     * Valid range: -180.0 to +180.0, where negative values represent the Western Hemisphere.
     */
    @NonNull
    Double minLng;

    /**
     * The maximum longitude (eastern boundary) of the rectangle in decimal degrees.
     * Valid range: -180.0 to +180.0, where positive values represent the Eastern Hemisphere.
     */
    @NonNull
    Double maxLng;
}
