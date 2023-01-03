package com.traveltime.sdk.dto.requests.timemapfast.levelofdetail;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@AllArgsConstructor
@Value
public class SimpleLevelOfDetail implements LevelOfDetail {
    @NonNull
    Level level;
}
