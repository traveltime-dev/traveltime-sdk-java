package com.traveltime.sdk.dto.requests.timemapfast.levelofdetail;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@AllArgsConstructor
@Value
public class SimpleNumericLevelOfDetail implements LevelOfDetail {
    @NonNull
    @Min(value = -20, message = "level should be between -20 and 2")
    @Max(value = 2, message = "level should be between -20 and 2")
    Integer level;
}
