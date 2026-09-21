package com.traveltime.sdk.dto.requests.distancemap;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.traveltime.sdk.dto.common.Coordinates;
import com.traveltime.sdk.dto.common.Coords;
import com.traveltime.sdk.dto.common.PolygonsFilter;
import com.traveltime.sdk.dto.common.RenderMode;
import com.traveltime.sdk.dto.common.Snapping;
import com.traveltime.sdk.dto.common.levelofdetail.LevelOfDetail;
import com.traveltime.sdk.dto.common.transportation.DistanceMapTransportation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import java.time.Instant;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DepartureSearch {
    @NonNull
    String id;

    @NonNull
    @Getter(AccessLevel.NONE)
    Coords coords;

    @Valid
    @NonNull
    DistanceMapTransportation transportation;

    @NonNull
    Instant departureTime;

    /**
     * Maximum journey distance in metres. Must be between 75 and 4,800,000.
     */
    @NonNull
    @Positive(message = "travelDistance should be positive")
    Integer travelDistance;

    LevelOfDetail levelOfDetail;

    @Valid
    PolygonsFilter polygonsFilter;

    /**
     * Enable to remove holes from returned polygons.
     * Note that this will likely result in loss in accuracy.
     */
    Boolean noHoles;

    @Valid
    Snapping snapping;

    RenderMode renderMode;

    Integer bufferDistance;

    /**
     * When true (API default), the returned shape will not cover large nearby water bodies.
     * Set to false to allow the shape to cover water bodies like large lakes, wide rivers, and seas.
     */
    Boolean removeWaterBodies;

    /**
     * @deprecated The location may now be an {@link com.traveltime.sdk.dto.common.H3CentroidCoords} or {@link com.traveltime.sdk.dto.common.GeohashCentroidCoords}, which this getter
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
