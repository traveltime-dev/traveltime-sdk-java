package com.traveltime.sdk.dto.common;

import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
public class Fares {
    @NonNull
    List<FareBreakdown> breakdown;
    @NonNull
    List<Ticket> ticketsTotal;
}
