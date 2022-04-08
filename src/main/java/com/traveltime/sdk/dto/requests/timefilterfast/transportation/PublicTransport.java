package com.traveltime.sdk.dto.requests.timefilterfast.transportation;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.traveltime.sdk.dto.requests.timefilterfast.Transportation;
import jakarta.validation.constraints.Positive;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PublicTransport implements Transportation {
    @Positive(message = "walkingTime must be greater than 0")
    Integer walkingTime;
}