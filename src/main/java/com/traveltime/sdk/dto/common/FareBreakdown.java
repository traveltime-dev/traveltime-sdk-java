package com.traveltime.sdk.dto.common;

import jakarta.validation.Valid;
import java.util.List;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
public class FareBreakdown {
    /**
     * Transportation modes covered by the ticket, for example, car, parking, boarding...
     */
    // TODO: Convert to Enum
    @NonNull
    List<String> modes;

    /**
     * Id's of route parts that are covered by these tickets.
     */
    @NonNull
    List<Integer> routePartIds;

    /**
     * Collection of tickets required to complete the journey.
     * <p>
     * Each ticket may cover different aspects of the journey, including various transportation modes and route segments.
     */
    @Valid
    @NonNull
    List<Ticket> tickets;
}
