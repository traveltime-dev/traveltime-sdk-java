package com.traveltime.sdk.dto.requests.timemapfast.levelofdetail;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@AllArgsConstructor
@Value
public class CoarseGridLevelOfDetail implements LevelOfDetail {
  @NonNull
  @Min(value = 600, message = "squareSize should be more than 600")
  Integer squareSize;
}
