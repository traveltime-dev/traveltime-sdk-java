package com.traveltime.sdk.dto.requests.postcodes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.traveltime.sdk.dto.common.Coordinates;
import com.traveltime.sdk.dto.common.Coords;
import com.traveltime.sdk.dto.common.FullRange;
import com.traveltime.sdk.dto.common.Property;
import com.traveltime.sdk.dto.common.Snapping;
import com.traveltime.sdk.dto.common.transportation.Transportation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import java.time.Instant;
import java.util.List;
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
    Transportation transportation;

    @NonNull
    Instant departureTime;

    @NonNull
    @Positive(message = "travelTime should be positive")
    Integer travelTime;

    @NonNull
    @Singular
    List<Property> properties;

    @Valid
    FullRange range;

    Snapping snapping;

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
