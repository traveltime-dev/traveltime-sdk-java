package com.traveltime.sdk.dto.common;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.jackson.Jacksonized;

@Getter
@Jacksonized
@Builder
@AllArgsConstructor
public class Fares {
    @Valid
    @NonNull
    Iterable<FareBreakdown> breakdown;
    @Valid
    @NonNull
    Iterable<Ticket> ticketsTotal;
}
