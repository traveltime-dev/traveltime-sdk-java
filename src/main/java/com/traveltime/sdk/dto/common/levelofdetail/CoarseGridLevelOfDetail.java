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
     *
     * Small value (higher detail):    Large value (lower detail):
     *
     * +--+--+--+--+--+--+            +--------+--------+
     * |  |  |  |  |  |  |            |        |        |
     * +--+--+--+--+--+--+            |        |        |
     * |  |  |  |  |  |  |            +--------+--------+
     * +--+--+--+--+--+--+            |        |        |
     * |  |  |  |  |  |  |            |        |        |
     * +--+--+--+--+--+--+            +--------+--------+
     *
     * A smaller value creates a finer grid with more detail but requires more processing power.
     * A larger value creates a coarser grid with less detail and improved performance.
     *
     * @param squareSize the size of each grid cell in metres
     */
    @NonNull
    @Min(value = 600, message = "squareSize should be more than 600")
    Integer squareSize;
}
