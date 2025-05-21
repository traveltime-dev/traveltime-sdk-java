package com.traveltime.sdk.dto.requests.timefilterfast;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.traveltime.sdk.dto.common.Property;
import com.traveltime.sdk.dto.common.Snapping;
import com.traveltime.sdk.dto.common.transportationfast.Transportation;
import java.util.List;
import lombok.*;
import lombok.extern.jackson.Jacksonized;
/**
 * Represents a one-to-many routing request where a single departure location is mapped to multiple arrival locations.
 * <p>
 * This class is useful for scenarios such as finding optimal routes from one starting point to multiple destinations
 * (e.g., delivery routes from a warehouse to customer locations).
 */
@Value
@Builder
@Jacksonized
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OneToMany {
    @NonNull
    String id;

    @NonNull
    String departureLocationId;

    @NonNull
    @Singular
    List<String> arrivalLocationIds;

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
