package com.traveltime.sdk.dto.responses.timefilter;

import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Getter
@Jacksonized
@Builder
public class Breakdown {
    Iterable<String> modes; // Will be enum
    Iterable<Integer> routePartIds;
    Iterable<Ticket> tickets;
}
