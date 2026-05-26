package com.traveltime.sdk.dto.requests.timefilterfast;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.*;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

/**
 * Represents a many-to-one routing request where multiple departure locations are mapped to a single arrival location.
 * <p>
 * This class is useful for scenarios such as finding optimal routes from multiple starting points to a common destination
 * (e.g., several employees traveling to the same office, or delivery vehicles returning to a central depot).
 */
@SuperBuilder
@Getter
@Jacksonized
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ManyToOne extends BaseSearch {

    @NonNull
    String arrivalLocationId;

    @NonNull
    @Singular
    List<String> departureLocationIds;
}
