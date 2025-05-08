package com.traveltime.sdk.dto.common.transportation;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.traveltime.sdk.dto.common.MaxChanges;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Coach implements Transportation {
  @Positive(message = "ptChangeDelay must be greater than 0")
  Integer ptChangeDelay;

  @Positive(message = "walkingTime must be greater than 0")
  Integer walkingTime;

  @Valid MaxChanges maxChanges;
}
