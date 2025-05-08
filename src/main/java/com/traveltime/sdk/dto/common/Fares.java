package com.traveltime.sdk.dto.common;

import jakarta.validation.Valid;
import java.util.List;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

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
