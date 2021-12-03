package com.traveltime.sdk.dto.responses.timefilterfast;

import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
public class Fares {
    @NonNull
    List<Ticket> ticketsTotal;
}
