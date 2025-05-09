package com.traveltime.sdk.dto.responses.timefilterfast;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Properties {
    @NonNull
    Integer travelTime;

    Fares fares;
}
