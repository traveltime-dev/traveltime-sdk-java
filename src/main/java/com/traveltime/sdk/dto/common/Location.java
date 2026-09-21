package com.traveltime.sdk.dto.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
public class Location {
    @NonNull
    String id;

    @NonNull
    @Getter(AccessLevel.NONE)
    Coords coords;

    /**
     * @deprecated The location may now be an {@link H3CentroidCoords} or {@link GeohashCentroidCoords}, which this getter
     * cannot represent — it returns null when built with a centroid. Use {@link #getLocation()}.
     */
    @Deprecated
    @JsonIgnore
    public Coordinates getCoords() {
        return coords instanceof Coordinates ? (Coordinates) coords : null;
    }

    @JsonProperty("coords")
    public Coords getLocation() {
        return coords;
    }
}
