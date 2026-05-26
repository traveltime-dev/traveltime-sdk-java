package com.traveltime.sdk.dto.requests.timefilter;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.Instant;
import java.util.List;
import lombok.*;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@SuperBuilder
@Getter
@Jacksonized
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArrivalSearch extends BaseSearch {
    @NonNull
    @Singular
    List<String> departureLocationIds;

    @NonNull
    String arrivalLocationId;

    @NonNull
    Instant arrivalTime;
}
