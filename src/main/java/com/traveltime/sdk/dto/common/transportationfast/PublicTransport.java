package com.traveltime.sdk.dto.common.transportationfast;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
@NoArgsConstructor(force = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PublicTransport implements Transportation {
    /**
     * Maximum time in seconds spent walking. Must be non-negative and less than or equal
     * to 1800. If null, the server determines the default value.
     */
    @PositiveOrZero(message = "walkingTime must not be negative")
    Integer walkingTime;
}
