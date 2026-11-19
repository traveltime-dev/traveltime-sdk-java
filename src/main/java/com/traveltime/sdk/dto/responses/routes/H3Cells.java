package com.traveltime.sdk.dto.responses.routes;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.traveltime.sdk.dto.responses.h3.Cell;
import java.util.List;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

/**
 * H3 cells a route passes through.
 */
@Value
@Builder
@Jacksonized
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class H3Cells {
    @NonNull
    List<Cell> cells;
}
