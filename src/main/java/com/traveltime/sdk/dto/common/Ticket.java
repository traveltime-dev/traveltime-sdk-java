package com.traveltime.sdk.dto.common;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.jackson.Jacksonized;

@Getter
@Jacksonized
@Builder
@AllArgsConstructor
public class Ticket {
    @NonNull
    String type;
    @Positive(message = "price must be greater than 0")
    double price;
    @NonNull
    String currency;
}
