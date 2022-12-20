package com.traveltime.sdk.dto.requests.timemapfast.levelofdetail;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@AllArgsConstructor
public class CoarseGridLevelOfDetail implements LevelOfDetail {
  @NonNull
  Integer squareSize;
}
