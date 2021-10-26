package com.traveltime.sdk.dto.responses.timefilter;

import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Getter
@Jacksonized
@Builder
public class Fares {
    Iterable<Breakdown> breakdown;
    Iterable<Ticket> ticketsTotal;
}
