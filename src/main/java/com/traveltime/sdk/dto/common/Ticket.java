package com.traveltime.sdk.dto.common;

import jakarta.validation.constraints.Positive;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
public class Ticket {
    @NonNull
    String type;

    @NonNull
    @Positive(message = "price must be greater than 0")
    Double price;

    @NonNull
    String currency;
}
