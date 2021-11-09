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
public class FareBreakdown {
    @NonNull
    Iterable<String> modes;
    @NonNull
    Iterable<Integer> routePartIds;
    @Valid
    @NonNull
    Iterable<Ticket> tickets;
}
