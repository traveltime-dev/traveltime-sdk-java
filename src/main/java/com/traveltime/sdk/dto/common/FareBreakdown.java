package com.traveltime.sdk.dto.common;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Getter
@Jacksonized
@Builder
@AllArgsConstructor
public class FareBreakdown {
    @NonNull
    List<String> modes;
    @NonNull
    List<Integer> routePartIds;
    @Valid
    @NonNull
    List<Ticket> tickets;
}
