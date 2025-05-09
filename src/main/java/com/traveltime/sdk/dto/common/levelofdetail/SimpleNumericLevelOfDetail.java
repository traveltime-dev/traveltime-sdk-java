package com.traveltime.sdk.dto.common.levelofdetail;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Builder
@AllArgsConstructor
@Value
public class SimpleNumericLevelOfDetail implements LevelOfDetail {
    /**
     * The numeric level of detail value. Allows to pick less details compared to SimpleLevelOfDetail.
     * <p>
     * Valid values range from -20 (least detailed) to 2 (most detailed).
     * <p>
     * Values correspond to simple detail levels as follows:
     * <ul>
     *   <li>-20 to -3: Progressively less detailed than LOWEST</li>
     *   <li>-2: Equivalent to LOWEST</li>
     *   <li>-1: Equivalent to LOW</li>
     *   <li> 0: Equivalent to MEDIUM</li>
     *   <li> 1: Equivalent to HIGH</li>
     *   <li> 2: Equivalent to HIGHEST</li>
     * </ul>
     */
    @NonNull
    @Min(value = -20, message = "level should be between -20 and 2")
    @Max(value = 2, message = "level should be between -20 and 2")
    Integer level;
}
