package com.traveltime.sdk.dto.requests.routes;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.traveltime.sdk.dto.requests.cell.Property;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import java.util.List;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

/**
 * H3 representation of the routes a search returns. Applies only when the search's properties include
 * {@link com.traveltime.sdk.dto.common.Property#H3}.
 */
@Value
@Builder
@Jacksonized
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class H3Params {
    /**
     * H3 cell resolution. The API default applies when null.
     */
    @Min(value = 6, message = "resolution should be between 6 and 11")
    @Max(value = 11, message = "resolution should be between 6 and 11")
    Integer resolution;

    /**
     * Travel time statistics to return for each cell. Cells carry only their id when null.
     */
    List<Property> properties;
}
