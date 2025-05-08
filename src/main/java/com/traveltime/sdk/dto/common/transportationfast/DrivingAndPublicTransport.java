package com.traveltime.sdk.dto.common.transportationfast;

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
public class DrivingAndPublicTransport implements Transportation {
  @Positive(message = "parkingTime must be greater than 0")
  Integer parkingTime;
}
