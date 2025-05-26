package com.traveltime.sdk.dto.requests.timefilterfast;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.traveltime.sdk.dto.common.Property;
import com.traveltime.sdk.dto.common.Snapping;
import com.traveltime.sdk.dto.common.transportationfast.Transportation;
import java.util.List;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

/**
 * Represents a many-to-one routing request where multiple departure locations are mapped to a single arrival location.
 * <p>
 * This class is useful for scenarios such as finding optimal routes from multiple starting points to a common destination
 * (e.g., several employees traveling to the same office, or delivery vehicles returning to a central depot).
 */
@Value
@Builder
@Jacksonized
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ManyToOne {
    @NonNull
    String id;

    @NonNull
    String arrivalLocationId;

    @NonNull
    @Singular
    List<String> departureLocationIds;

    @NonNull
    Transportation transportation;

    @NonNull
    Integer travelTime;

    @NonNull
    String arrivalTimePeriod;

    @NonNull
    @Singular
    List<Property> properties;

    Snapping snapping;
}
