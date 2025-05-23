package com.traveltime.sdk.dto.requests.zones;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.traveltime.sdk.dto.common.Coordinates;
import com.traveltime.sdk.dto.common.FullRange;
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
public class ArrivalSearch {
    @NonNull
    String id;

    @NonNull
    Coordinates coords;

    @NonNull
    Transportation transportation;

    @NonNull
    Instant arrivalTime;

    @NonNull
    @Positive(message = "travelTime must be greater than 0")
    Integer travelTime;

    /**
     * Specifies the minimum percentage of reachable postcodes required for a sector to be included in results.
     * <p>
     * This threshold is expressed as a decimal value between 0.0 and 1.0, where:
     * - 0.0 means sectors with any reachable postcodes (even just one) will be included
     * - 0.5 means only sectors where at least 50% of postcodes are reachable will be included
     * - 1.0 means only sectors where 100% of postcodes are reachable will be included
     * <p>
     * For example, if set to 0.75, a sector containing 100 postcodes would only appear in results
     * if at least 75 of those postcodes can be reached within the specified travel time.
     * <p>
     * This parameter helps filter results to focus on areas with meaningful coverage,
     * avoiding sectors that are technically reachable but have minimal practical accessibility.
     */
    @NonNull
    Double reachablePostcodesThreshold;

    @NonNull
    @Singular
    List<Property> properties;

    @Valid
    FullRange range;
}
