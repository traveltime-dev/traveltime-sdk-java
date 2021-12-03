package com.traveltime.sdk.dto.common;

import jakarta.validation.Valid;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
public class Fares {
    @Valid
    @NonNull
    List<FareBreakdown> breakdown;
    @Valid
    @NonNull
    List<Ticket> ticketsTotal;
}
