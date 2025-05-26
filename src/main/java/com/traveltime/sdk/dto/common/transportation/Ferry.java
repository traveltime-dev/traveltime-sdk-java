package com.traveltime.sdk.dto.common.transportation;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Positive;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

/**
 * Represents a multi-modal journey combining walking and taking a ferry.
 */
@Value
@Builder
@Jacksonized
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Ferry implements Transportation {
    /**
     * Time in seconds required to board a ferry.
     * If null, `boardingTime` is 0.
     */
    @Positive(message = "boardingTime must be greater than 0")
    Integer boardingTime;
}
