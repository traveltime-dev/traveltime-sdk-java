package com.traveltime.sdk.dto.common.levelofdetail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Builder
@AllArgsConstructor
@Value
public class SimpleLevelOfDetail implements LevelOfDetail {
    /**
     * Specifies the detail level of the returned shape.
     *
     * +----------------+---------------------------------------------+
     * | Level          | Result                                      |
     * +----------------+---------------------------------------------+
     * | LOWEST         | Minimal detail, maximum performance         |
     * | LOW            | Reduced detail, improved performance        |
     * | MEDIUM         | Balanced detail and performance             |
     * | HIGH           | Enhanced detail, reduced performance        |
     * | HIGHEST        | Maximum detail, minimum performance         |
     * +----------------+---------------------------------------------+
     *
     */
    @NonNull
    Level level;
}
