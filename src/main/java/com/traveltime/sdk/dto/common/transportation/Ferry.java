package com.traveltime.sdk.dto.common.transportation;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Positive;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Ferry implements Transportation{
    @Positive(message = "boardingTime must be greater than 0")
    Integer boardingTime;
}
