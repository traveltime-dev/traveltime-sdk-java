package com.traveltime.sdk.dto.requests.timefilterfast;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.*;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

/**
 * Represents a one-to-many routing request where a single departure location is mapped to multiple arrival locations.
 * <p>
 * This class is useful for scenarios such as finding optimal routes from one starting point to multiple destinations
 * (e.g., delivery routes from a warehouse to customer locations).
 */
@SuperBuilder
@Jacksonized
@Getter
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OneToMany extends BaseSearch {

    @NonNull
    String departureLocationId;

    @NonNull
    @Singular
    List<String> arrivalLocationIds;
}
