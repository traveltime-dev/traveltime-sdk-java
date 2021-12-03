package com.traveltime.sdk.dto.responses.postcodes;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Property {
    @Positive(message = "travelTime should be positive")
    Integer travelTime;
    @Positive(message = "distance should be positive")
    Integer distance;
}
