package com.traveltime.sdk.dto.common.levelofdetail;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
@AllArgsConstructor
@Value
public class CoarseGridLevelOfDetail implements LevelOfDetail {
    /**
     * Specifies the grid cell size (in metres) used to construct the shape.
     * This parameter controls the level of detail in the resulting shape.
     * <p>
     * A smaller value creates a finer grid with more detail but requires more processing power.
     * A larger value creates a coarser grid with less detail and improved performance.
     * <p>
     * <pre>
     * Small value (higher detail):    Large value (lower detail):
     * +--+--+--+--+--+--+            +--------+--------+
     * |  |  |  |  |  |  |            |        |        |
     * +--+--+--+--+--+--+            |        |        |
     * |  |  |  |  |  |  |            +--------+--------+
     * +--+--+--+--+--+--+            |        |        |
     * |  |  |  |  |  |  |            |        |        |
     * +--+--+--+--+--+--+            +--------+--------+
     * </pre>
     *
     */
    @NonNull
    @Min(value = 600, message = "squareSize should be more than 600")
    Integer squareSize;
}
