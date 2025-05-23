package com.traveltime.sdk.dto.common;

import jakarta.validation.constraints.Positive;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
public class Ticket {
    /**
     * Type of the ticket representing ticket duration
     */
    // TODO: Convert to Enum
    @NonNull
    String type;

    /**
     * Price of the ticket
     */
    @NonNull
    @Positive(message = "price must be greater than 0")
    Double price;

    /**
     * Currency code of the ticket written in an ISO 4217 standard
     */
    @NonNull
    String currency;
}
