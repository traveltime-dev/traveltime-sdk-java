package com.traveltime.sdk.dto.common;

import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
public class FareBreakdown {
    @NonNull
    List<String> modes;
    @NonNull
    List<Integer> routePartIds;
    @NonNull
    List<Ticket> tickets;
}
